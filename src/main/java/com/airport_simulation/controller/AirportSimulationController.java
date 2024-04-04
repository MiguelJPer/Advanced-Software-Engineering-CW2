package src.main.java.com.airport_simulation.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import src.main.java.com.airport_simulation.data_structure.Passenger;
import src.main.java.com.airport_simulation.model.CheckInDesk;
import src.main.java.com.airport_simulation.model.PassengerQueue;

public class AirportSimulationController {

    @FXML
    private ListView<String> checkInDeskListView; // ListView for check-in desk information

    @FXML
    private ListView<String> flightInfoListView; // ListView for flight information

    @FXML
    private ListView<String> passengerQueueListView; // UI component for displaying passenger queue information

    private ObservableList<String> checkInDeskMessages = FXCollections.observableArrayList();
    private ObservableList<String> flightInfoForDisplay = FXCollections.observableArrayList();
    private ObservableList<String> passengerQueueInfo = FXCollections.observableArrayList();

    public void initialize() {
        checkInDeskListView.setItems(checkInDeskMessages);
        flightInfoListView.setItems(flightInfoForDisplay);
        passengerQueueListView.setItems(passengerQueueInfo);
        // Simplified presentation of simulation startup logic and thread startup code
        startSimulation();
    }

    private void startSimulation() {
        ObservableList<Passenger> passengerList = FXCollections.observableArrayList();
        PassengerQueue passengerQueue = new PassengerQueue(passengerList);

        // Sets a callback for when a new passenger is added to the queue
        passengerQueue.setOnNewPassenger(passenger -> {
            String passengerInfo = formatPassengerInfo(passenger);
            // Ensures updates to the UI are made on the JavaFX Application Thread
            Platform.runLater(() -> checkInDeskMessages.add(passengerInfo));
        });

        // Initializes and starts threads for each CheckInDesk
        for (int i = 0; i < 2; i++) {
            CheckInDesk checkInDesk = new CheckInDesk(passengerList);
            // Registers callback for processing passengers
            checkInDesk.setOnPassengerProcessed(this::updateUIWithPassengerInfo);
            // Registers callback for flight updates
            checkInDesk.setOnFlightUpdate(flightInfo -> Platform.runLater(() -> flightInfoForDisplay.add(flightInfo)));

            new Thread(checkInDesk, "CheckInDesk-" + i).start();
        }
    }

    // Method to format passenger information for display
    private String formatPassengerInfo(Passenger passenger) {
        // Formats passenger information, such as name and flight code
        return passenger.getName() + " - " + passenger.getFlightCode();
    }

    // Updates UI to display passenger information
    public void updateUIWithPassengerInfo(String message) {
        // Schedules the UI update to run on the JavaFX Application Thread
        Platform.runLater(() -> checkInDeskMessages.add(message));
    }

    private void updateUIWithFlightInfo(String flightInfo) {
        // Schedules the UI update to run on the JavaFX Application Thread
        Platform.runLater(() -> flightInfoForDisplay.add(flightInfo));
    }

    // Binds check-in desk information to the UI
    public void bindCheckInDesks(ObservableList<String> messages) {
        checkInDeskListView.setItems(messages);
    }

    // Binds flight information to the UI
    public void bindFlightInfo(ObservableList<String> flightInfo) {
        flightInfoListView.setItems(flightInfo);
    }

    // Method to bind PassengerQueue information to the UI
    public void bindPassengerQueue(ObservableList<Passenger> passengerQueue) {
        passengerQueue.forEach(passenger -> {
            String passengerInfo = formatPassengerInfo(passenger); // Assumes this method formats the Passenger object into a string
            passengerQueueInfo.add(passengerInfo); // Adds the formatted passenger information to the UI list
        });

        // Listens for changes in PassengerQueue, updating the UI whenever a new passenger joins
    }

}
