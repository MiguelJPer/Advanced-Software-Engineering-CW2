package src;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.DataStructure.Passenger;

import java.io.*;
import java.util.*;

public class FlightReportGeneratorUI extends Application {

    private Map<String, FlightReport> flightReports;

    @Override
    public void start(Stage primaryStage) {
        flightReports = new HashMap<>();
        readCheckedInPassengerData();
        generateFlightReports();

        Button generateReportButton = new Button("Generate Flight Reports");
        generateReportButton.setOnAction(event -> {
            generateFlightReports();
            showSuccessAlert("Flight reports generated successfully.");
        });

        VBox root = new VBox(generateReportButton);
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Flight Report Generator");
        primaryStage.show();
    }

    private void readCheckedInPassengerData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Datasets/CheckedInPassenger.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 10) {
                    String flightCode = data[2];
                    double luggageWeight = Double.parseDouble(data[7]);
                    double luggageVolume = Double.parseDouble(data[8]);
                    double excessFee = Double.parseDouble(data[9]);

                    FlightReport flightReport = flightReports.getOrDefault(flightCode, new FlightReport());
                    flightReport.incrementPassengerCount();
                    flightReport.addToTotalLuggageWeight(luggageWeight);
                    flightReport.addToTotalLuggageVolume(luggageVolume);
                    flightReport.addToTotalExcessFee(excessFee);
                    flightReports.put(flightCode, flightReport);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Error reading CheckedInPassenger data.");
        }
    }

    private void generateFlightReports() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Datasets/FlightReports.txt"))) {
            for (Map.Entry<String, FlightReport> entry : flightReports.entrySet()) {
                String flightCode = entry.getKey();
                FlightReport flightReport = entry.getValue();
                writer.write("Flight Code: " + flightCode);
                writer.newLine();
                writer.write("Passenger Count: " + flightReport.getPassengerCount());
                writer.newLine();
                writer.write("Total Luggage Weight: " + flightReport.getTotalLuggageWeight());
                writer.newLine();
                writer.write("Total Luggage Volume: " + flightReport.getTotalLuggageVolume());
                writer.newLine();
                writer.write("Total Excess Fee: " + flightReport.getTotalExcessFee());
                writer.newLine();
                writer.write("Exceeded Capacity: " + (flightReport.isExceededCapacity() ? "Yes" : "No"));
                writer.newLine();
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
