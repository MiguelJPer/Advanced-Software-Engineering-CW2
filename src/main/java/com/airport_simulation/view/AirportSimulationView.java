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

        controller.startSimulation();

        // Set up the primary stage of the application.
        primaryStage.setTitle("Airport Simulation");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Start the JavaFX application.
    }
}
