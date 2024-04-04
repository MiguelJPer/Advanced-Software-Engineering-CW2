package src.main.java.com.airport_simulation.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import src.main.java.com.airport_simulation.controller.PassengerQueueController;
import src.main.java.com.airport_simulation.model.PassengerQueue;

public class PassengerQueueView extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the PassengerQueueView FXML file to set up the UI layout.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/main/resources/com/airport_simulation/view/PassengerQueueView.fxml"));
        Parent root = loader.load();

        // Get the PassengerQueueController associated with the loaded FXML.
        PassengerQueueController controller = loader.getController();
        // Create a PassengerQueue object and pass it to the Controller.
        PassengerQueue queue = new PassengerQueue();
        controller.setPassengerQueue(queue);

        // Set the title of the application window and establish the scene.
        primaryStage.setTitle("Airport Simulation");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        // Start the PassengerQueue thread (assuming PassengerQueue implements Runnable).
        new Thread(queue).start();
    }

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application.
    }
}
