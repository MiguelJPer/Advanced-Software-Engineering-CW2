package src.main.java.com.airport_simulation.model;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import src.main.java.com.airport_simulation.data_structure.Flight;
import src.main.java.com.airport_simulation.data_structure.Passenger;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;

public class CheckInDesk implements Runnable {
    private ObservableList<Passenger> queue; // The queue of passengers waiting to be processed.
    private boolean isRunning; // Indicates if the check-in desk thread should continue running.
    private double baggageFee = 0; // Stores the baggage fee calculated for a passenger.
    private Map<String, Flight> flightsData = new HashMap<>(); // Stores flight information, keyed by flight code.

    // An ObservableList for displaying flight information in the UI.
    private ObservableList<String> flightInfoForDisplay = FXCollections.observableArrayList();

    private Consumer<String> onPassengerProcessed; // A callback for when a passenger has been processed.
    public void setOnPassengerProcessed(Consumer<String> listener) {
        this.onPassengerProcessed = listener;
    }

    // Constructor that accepts a queue of passengers and a map of flight data.
    public CheckInDesk(ObservableList<Passenger> queue, Map<String, Flight> flightMap) {
        this.queue = queue;
        this.isRunning = true;
        // Check if an external flightMap was provided.
        if (flightMap != null) {
            this.flightsData = flightMap;
        } else {
            loadFlightsData(); // Load internal flight data if not provided.
        }
        updateFlightInfoForDisplay();
    }

    // An alternative constructor that does not require an external flightMap.
    public CheckInDesk(ObservableList<Passenger> queue) {
        this(queue, null); // Calls the main constructor with null for the flightMap.
    }

    @Override
    public void run() {
        while (isRunning) {
            processNextPassenger();
            try {
                Thread.sleep(5000); // Simulates the time taken to process a passenger.
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrupted.");
                Thread.currentThread().interrupt();
            }
        }
    }

    // Processes the next passenger in the queue.
    public void processNextPassenger() {
        synchronized (queue) {
            while (queue.isEmpty()) {
                try {
                    queue.wait(); // Wait until the queue is not empty.
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Re-set the interrupt status.
                    return; // Or handle the exception as appropriate.
                }
            }
            Passenger passenger = queue.remove(0); // Retrieves the next passenger.
            String flightCode = passenger.getFlightCode(); // Gets the passenger's flight code.
            Flight flight = flightsData.get(flightCode); // Retrieves the corresponding Flight object.

            if (flight != null) {
                // Process passenger data.
                double excessWeight = passenger.getBaggageWeight() - flight.getFreeLuggageAllowance();
                if (excessWeight > 0) {
                    baggageFee = excessWeight * flight.getExcessLuggageCharge();
                }
                // Ensure the passenger processing message is displayed in the UI.
                String message = passenger.getName() + " is dropping off one bag of " + passenger.getBaggageWeight() + ". A baggage fee of " + baggageFee + " is due.";
                Platform.runLater(() -> {
                    if (onPassengerProcessed != null) {
                        onPassengerProcessed.accept(message);
                    }
                });

                // Updates flight data.
                updateFlightsData(flight, passenger);
                updateFlightInfoForDisplay(); // Updates the list displayed in the UI.

            } else {
                System.out.println("No flight found for flight code: " + flightCode);
            }
        }

    }

    // Updates the state of a Flight object.
    private void updateFlightsData(Flight flight, Passenger passenger){
        // Increase the count of checked-in passengers and the total luggage weight for the flight.
        flight.setCheckedInPassengers(flight.getCheckedInPassengers() + 1);
        flight.setCarriedLuggageWeight(flight.getCarriedLuggageWeight() + passenger.getBaggageWeight());
        // Persist data changes.
        updateFlightsCsv();
    }

    // Called after each passenger is processed or when flight data is updated to refresh the UI list.
    private void updateFlightInfoForDisplay() {
        Platform.runLater(() -> {
            flightInfoForDisplay.clear(); // Clears the current list.
            for (Map.Entry<String, Flight> entry : flightsData.entrySet()) {
                String flightCode = entry.getKey();
                Flight flight = entry.getValue();
                String displayText = flightCode + ": Checked-in passenger count " + flight.getCheckedInPassengers()
                        + ", Total luggage weight " + flight.getCarriedLuggageWeight();
                flightInfoForDisplay.add(displayText); // Adds the updated info to the UI list.
            }
        });
    }

    private void loadFlightsData() {
        // Method to load flight data, typically from a CSV file.
    }

    // Updates the flights.csv file with current flight data.
    private void updateFlightsCsv() {
        // Method to write updated flight data back to the CSV.
    }

    // Stops the check-in desk's processing loop.
    public void stopRunning() {
        this.isRunning = false;
    }

    // Provides external access to the updated flight information list for UI display.
    public ObservableList<String> getFlightInfoForDisplay() {
        return flightInfoForDisplay;
    }

}
