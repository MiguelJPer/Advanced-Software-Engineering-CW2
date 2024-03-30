package f21as.flightinfo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;

public class App extends Application {
    private Map<String, Flight> flights = new HashMap<>();
    @Override
    public void start(Stage stage) {
    loadCheckInData();
    for (Flight flight : flights.values()) {
        int checkedInCount = flight.getCheckedInCount();
        int capacity = flight.getCapacity();
        double holdVolume = flight.getHoldVolume();
        double totalBaggageVolume = flight.getBaggageVolume();
  
        double holdFullnessPercentage = (totalBaggageVolume / holdVolume);

        // Display hold fullness percentage and number of customers checked in
        System.out.printf("Flight %s: %.2f%% of hold full (%d of %d checked in)\n",
                flight.getFlightCode(), holdFullnessPercentage, checkedInCount, capacity);
    }
    ComboBox<String> flightCodeComboBox = new ComboBox<>();
    flightCodeComboBox.getItems().addAll(flights.keySet());
    flightCodeComboBox.setPromptText("Select Flight Code");

    Button showDetailsButton = new Button("Show Details");
    showDetailsButton.setOnAction(e -> {
        String selectedFlightCode = flightCodeComboBox.getValue();
        if (selectedFlightCode != null) {
            Flight selectedFlight = flights.get(selectedFlightCode);
            FlightDetailsWindow.display(selectedFlightCode, selectedFlight);
        }
    });

    VBox layout = new VBox(10);
    layout.setAlignment(Pos.CENTER);
    layout.getChildren().addAll(flightCodeComboBox, showDetailsButton);

    Scene scene = new Scene(layout, 400, 300);
    stage.setScene(scene);
    stage.show();
}

    private void loadCheckInData() {
    Thread loadFlightsThread = new Thread(this::loadFlights);
    Thread loadCheckInsThread = new Thread(this::loadCheckIns);
    loadFlightsThread.start();
    try {
        loadFlightsThread.join();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    loadCheckInsThread.start();
    try {
        loadCheckInsThread.join();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}

    private void loadFlights() {
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\pares\\OneDrive\\Documents\\NetBeansProjects\\flightinfo\\src\\main\\java\\f21as\\flightinfo\\flightList.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                // assuming the CSV file is in the format: flightCode,destination,carrier,capacity,holdVolume
                String flightCode = fields[0];
                String airlineName = fields[2];
                int capacity = Integer.parseInt(fields[4]);
                double holdVolume = Double.parseDouble(fields[5]);

                Flight flight = new Flight(flightCode, airlineName, capacity, holdVolume);
                flights.put(flightCode, flight);
                
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCheckIns() {
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\pares\\OneDrive\\Documents\\NetBeansProjects\\flightinfo\\src\\main\\java\\f21as\\flightinfo\\CheckedInPassenger.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                // assuming the CSV file is in the format: flightCode,passengerName,checkedIn,baggageVolume
                String flightCode = fields[2];
                String passengerName = fields[1];
                double baggageVolume = Double.parseDouble(fields[9]);

                Flight flight = flights.get(flightCode);
                if (flight != null) {
                        flight.incrementCheckedInCount();
                        flight.addBaggageVolume(baggageVolume);    
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch();
    }
}