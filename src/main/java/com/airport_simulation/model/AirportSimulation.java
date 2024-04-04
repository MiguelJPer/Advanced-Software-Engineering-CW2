package src.main.java.com.airport_simulation.model;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import src.main.java.com.airport_simulation.data_structure.Flight;
import src.main.java.com.airport_simulation.data_structure.Passenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class AirportSimulation {
    static {
        // Set the logging configuration file path.
        System.setProperty("java.util.logging.config.file", "./src/main/resources/com/airport_simulation/logging.properties");
    }

    public static void main(String[] args) {
        // UI-related code needs to be run in the JavaFX application thread.
        Platform.startup(() -> {
            // Create an instance of PassengerQueue with an ObservableList of passengers.
            ObservableList<Passenger> passengerList = FXCollections.observableArrayList();
            PassengerQueue passengerQueue = new PassengerQueue(passengerList); // Modify here to pass ObservableList

            // Create and start the PassengerQueue thread.
            Thread passengerQueueThread = new Thread(passengerQueue);
            passengerQueueThread.start();

            // Define the number of CheckInDesk instances.
            final int numberOfDesks = 2;

            // Create and start threads for each CheckInDesk instance.
            for (int i = 0; i < numberOfDesks; i++) {
                CheckInDesk checkInDesk = new CheckInDesk(passengerList); // Modify here to pass ObservableList
                Thread deskThread = new Thread(checkInDesk, "CheckInDesk-" + i);
                deskThread.start();
            }

            // Add a simple shutdown hook to gracefully stop all threads.
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Shutting down simulation...");
                passengerQueue.stopRunning(); // Stop the PassengerQueue thread.
                // Note: For CheckInDesk, a method to safely stop the thread may need to be implemented.
            }));

            // Wait for a certain time before ending the simulation.
            try {
                Thread.sleep(15000); // Assume the simulation runs for 15 seconds.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(0); // Trigger the shutdown hook to stop all threads.
        });
    }
}
