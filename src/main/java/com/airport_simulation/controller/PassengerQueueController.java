package src.main.java.com.airport_simulation.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import src.main.java.com.airport_simulation.data_structure.Passenger;
import src.main.java.com.airport_simulation.model.PassengerQueue;

import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PassengerQueueController {
    @FXML private VBox mainContainer; // The main container for UI components related to the passenger queue.
    @FXML private Label queueCountLabel; // A label to display the current number of passengers in the queue.
    @FXML private TableView<Passenger> passengerTable; // A table to display detailed information about passengers.
    @FXML private TableColumn<Passenger, String> flightCodeColumn; // Column for displaying passengers' flight codes.
    @FXML private TableColumn<Passenger, String> nameColumn; // Column for displaying passengers' names.
    @FXML private TableColumn<Passenger, Double> weightColumn; // Column for displaying passengers' baggage weight.
    @FXML private TableColumn<Passenger, String> dimensionsColumn; // Column for displaying passengers' baggage dimensions.

    private PassengerQueue passengerQueue; // A reference to the PassengerQueue object that should be passed in.

    @FXML
    public void initialize() {
        // Bind Passenger properties to the table columns.
        flightCodeColumn.setCellValueFactory(cellData -> cellData.getValue().flightCodeProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        weightColumn.setCellValueFactory(cellData -> cellData.getValue().baggageWeightProperty().asObject());
        dimensionsColumn.setCellValueFactory(cellData -> cellData.getValue().baggageDimensionsProperty());

        // Start a background thread to periodically update the UI.
        Thread updateThread = new Thread(() -> {
            while (true) {
                Platform.runLater(() -> {
                    passengerTable.setItems(passengerQueue.getPassengerList());
                    queueCountLabel.setText("There are currently " + passengerQueue.getQueueSize() + " people waiting in the queue:");
                });
                try {
                    Thread.sleep(1000); // Update the UI every second.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        updateThread.setDaemon(true); // Set the thread as a daemon so it doesn't prevent the application from exiting.
        updateThread.start();
    }

    // Method to set the PassengerQueue object from outside.
    public void setPassengerQueue(PassengerQueue queue) {
        this.passengerQueue = queue;
    }
}
