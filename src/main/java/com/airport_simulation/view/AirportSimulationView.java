package src.main.java.com.airport_simulation.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import src.main.java.com.airport_simulation.controller.AirportSimulationController;
import src.main.java.com.airport_simulation.model.CheckInDesk;
import src.main.java.com.airport_simulation.model.PassengerQueue;

public class AirportSimulationView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the main application view from FXML.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/main/resources/com/airport_simulation/view/AirportSimulationView.fxml"));
        Parent root = loader.load();

        // Get an instance of the controller associated with the loaded FXML.
        AirportSimulationController controller = loader.getController();

        // Create a PassengerQueue instance.
        ObservableList<PassengerQueue> passengerQueues = FXCollections.observableArrayList();
        PassengerQueue passengerQueue = new PassengerQueue(); // Assuming PassengerQueue is adapted to work with ObservableList.
        Thread passengerQueueThread = new Thread(passengerQueue);
        passengerQueueThread.setDaemon(true); // Set the thread as a daemon so it doesn't prevent the application from exiting.
        passengerQueueThread.start();

        // Create CheckInDesk instances.
        ObservableList<CheckInDesk> desks = FXCollections.observableArrayList();
        final int numberOfDesks = 2; // Define the number of check-in desks.
        for (int i = 0; i < numberOfDesks; i++) {
            CheckInDesk checkInDesk = new CheckInDesk(passengerQueue.getPassengerList()); // Pass the ObservableList of passengers to the CheckInDesk.
            Thread deskThread = new Thread(checkInDesk, "CheckInDesk-" + i);
            deskThread.setDaemon(true);
            deskThread.start();
            desks.add(checkInDesk);

            // Update the UI based on events from the CheckInDesk.
            checkInDesk.setOnPassengerProcessed(controller::updateUIWithPassengerInfo); // Assuming this method is defined in the controller.
        }

        // Bind the data to the controller for UI updates.
        controller.bindCheckInDesks(desks);
        controller.bindPassengerQueue(passengerQueue.getPassengerList());

        // Set up the primary stage of the application.
        primaryStage.setTitle("Airport Simulation");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Start the JavaFX application.
    }
}
