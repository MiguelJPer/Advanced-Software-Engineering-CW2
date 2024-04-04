package src.main.java.com.airport_simulation.model;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import src.main.java.com.airport_simulation.data_structure.Passenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class PassengerQueue implements Runnable {
    private ObservableList<Passenger> passengers = FXCollections.observableArrayList(); // List to hold the passengers in the queue.
    private static final Logger logger = Logger.getLogger(PassengerQueue.class.getName()); // Logger for logging status messages.
    private boolean running; // Indicates if the PassengerQueue thread should continue running.
    private Random random = new Random(); // Random generator for simulating passenger arrival times and details.
    private List<String> flightCodes; // List to hold flight codes for generating random passengers.
    private AtomicBoolean isFlightCodesLoaded = new AtomicBoolean(false); // Atomic flag to ensure flight codes are loaded only once.

    // Original constructor remains unchanged for backward compatibility
    public PassengerQueue() {
        this(FXCollections.observableArrayList()); // Calls the new constructor with an empty ObservableList for initialization.
    }

    // New constructor that accepts an external ObservableList<Passenger>
    public PassengerQueue(ObservableList<Passenger> passengers) {
        this.passengers = passengers;
        this.running = true;
        this.flightCodes = new ArrayList<>();
        logger.info("PassengerQueue initialized");
    }

    @Override
    public void run() {
        logger.info("PassengerQueue started running.");
        while (running) {
            try {
                Thread.sleep(random.nextInt(2000) + 500); // Randomly waits between 500 and 2500 milliseconds before generating a new passenger.
                if (flightCodes.isEmpty()) {
                    loadFlightCodes(); // Ensures flight codes are loaded before generating passengers.
                }
                Passenger passenger = generateRandomPassenger();
                // Updates the ObservableList of passengers on the JavaFX thread.
                Platform.runLater(() -> passengers.add(passenger));
            } catch (InterruptedException e) {
                logger.info("PassengerQueue thread interrupted.");
                running = false;
            }
        }
        logger.info("PassengerQueue stopped running.");
    }

    private void loadFlightCodes() {
        // Loads flight codes from a CSV file if not already loaded. Ensures this happens only once.
        if (isFlightCodesLoaded.compareAndSet(false, true)) {
            try (InputStream is = getClass().getResourceAsStream("/com/airport_simulation/dataset/flights.csv");
                 BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                reader.readLine(); // Skips the header row in the CSV.
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    flightCodes.add(data[0]); // Assumes the flight code is in the first column.
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Passenger generateRandomPassenger() {
        // Generates a random passenger using the loaded flight codes.
        logger.info("Generating random passenger...");
        if (!flightCodes.isEmpty()) {
            String flightCode = flightCodes.get(random.nextInt(flightCodes.size()));
            String name = "Passenger" + (random.nextInt(900) + 100); // Generates a name like "Passenger123".
            double baggageWeight = 20.0 + (20.0 * random.nextDouble()); // Generates a baggage weight between 20.0 and 40.0.
            double[] baggageDimensions = {
                    20.0 + (10.0 * random.nextDouble()), // Length between 20.0 and 30.0.
                    15.0 + (5.0 * random.nextDouble()),  // Width between 15.0 and 20.0.
                    10.0 + (5.0 * random.nextDouble())   // Height between 10.0 and 15.0.
            };
            logger.info("Random passenger generated.");
            return new Passenger(flightCode, name, baggageWeight, baggageDimensions);
        } else {
            logger.warning("Flight code list is empty, cannot generate random passenger.");
            return null; // Or return a default passenger instance or throw an exception.
        }
    }

    public ObservableList<Passenger> getPassengerList() {
        return passengers;
    }

    public int getQueueSize() {
        return passengers.size();
    }

    public void stopRunning() {
        this.running = false;
    }

    public Passenger createRandomPassenger() {
        return generateRandomPassenger();
    }

    public synchronized void addPassengerDirectly(Passenger passenger) {
        this.passengers.add(passenger); // Directly adds a passenger to the ObservableList.
    }

}
