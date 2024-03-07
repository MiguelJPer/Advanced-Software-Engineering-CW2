package src;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import src.DataStructure.Flight;
import src.DataStructure.Passenger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PassengerQueryUI extends Application {
    private List<Passenger> passengerList;
    private List<Flight> flightList;
    private Stage primaryStage;
    private Passenger currentPassenger;
    private Flight currentFlight;
    private Map<String, FlightReport> flightReports;


    public static class InvalidInputException extends Exception {
        public InvalidInputException(String message) {
            super(message);
        }
    }

    public static class PassengerNotFoundException extends Exception {
        public PassengerNotFoundException(String message) {
            super(message);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showMainScene();
    }

    private void showMainScene() {
        Button showButton = new Button("Query");
        showButton.setOnAction(event -> {
            try {
                showPassengerDataScene();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        VBox root = new VBox(showButton);
        Scene scene = new Scene(root, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Main Menu");
        primaryStage.show();
    }

    private void showPassengerDataScene() {
        try {
            // Load passenger data
            passengerList = PassengerDataHandler.passengerListGetter();
            // Load flight data
            flightList = FlightDataHandler.flightListGetter();
            // File Report
            flightReports = new HashMap<>();

            // Text field for input
            TextField inputField = new TextField();
            inputField.setPromptText("Enter Name or Booking Reference");

            // Button to execute query
            Button queryButton = new Button("Query");
            queryButton.setOnAction(event -> executeQuery(inputField.getText()));

            HBox inputBox = new HBox(inputField, queryButton);

            // TableView to display query results
            TableView<Passenger> tableView = new TableView<>();

            // Define columns
            TableColumn<Passenger, String> bookingRefCol = new TableColumn<>("Booking Reference");
            bookingRefCol.setCellValueFactory(new PropertyValueFactory<>("bookingReferenceCode"));

            TableColumn<Passenger, String> nameCol = new TableColumn<>("Name");
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<Passenger, String> flightCodeCol = new TableColumn<>("Flight Code");
            flightCodeCol.setCellValueFactory(new PropertyValueFactory<>("flightCode"));

            TableColumn<Passenger, String> checkedInCol = new TableColumn<>("Checked In");
            checkedInCol.setCellValueFactory(cellData -> {
                boolean checkedIn = cellData.getValue().isCheckedIn();
                return new SimpleStringProperty(checkedIn ? "Yes" : "No");
            });

            // Add columns to table
            tableView.getColumns().addAll(bookingRefCol, nameCol, flightCodeCol, checkedInCol);

            // Populate table with data
            tableView.setItems(FXCollections.observableArrayList(passengerList));

            // Button to return to main menu
            Button returnButton = new Button("Return");
            returnButton.setOnAction(event -> showMainScene());

            VBox root = new VBox(inputBox, tableView, returnButton);
            Scene scene = new Scene(root, 600, 400);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Passenger Query");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showInputErrorAlert();
        }
    }

    private void executeQuery(String query) {
        try {
            List<Passenger> queryResult = passengerList.stream()
                    .filter(passenger -> passenger.getName().equalsIgnoreCase(query)
                            || passenger.getBookingReferenceCode().equalsIgnoreCase(query))
                    .collect(Collectors.toList());

            if (queryResult.isEmpty()) {
                throw new PassengerNotFoundException("Passenger with name or booking reference not found: " + query);
            } else {
                currentPassenger = queryResult.get(0); // Assume only one passenger for simplicity
                showQueryResult(currentPassenger);
            }
        } catch (PassengerNotFoundException e) {
            showErrorAlert(e.getMessage());
        }
    }

    private void showQueryResult(Passenger passenger) {
        Label passengerLabel = new Label("Passenger Information:");
        Label nameLabel = new Label("Name: " + passenger.getName());
        Label bookingRefLabel = new Label("Booking Reference: " + passenger.getBookingReferenceCode());
        Label flightCodeLabel = new Label("Flight Code: " + passenger.getFlightCode());
        Label checkedInLabel = new Label("Checked In: " + (passenger.isCheckedIn() ? "Yes" : "No"));

        // Button to confirm passenger information
        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(event -> showLuggageInputPage(passenger));

        VBox passengerInfoBox = new VBox(passengerLabel, nameLabel, bookingRefLabel, flightCodeLabel, checkedInLabel, confirmButton);

        // Button to return to previous scene
        Button returnButton = new Button("Return");
        returnButton.setOnAction(event -> {
            try {
                showPassengerDataScene();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        VBox root = new VBox(passengerInfoBox, returnButton);
        Scene scene = new Scene(root, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Passenger Information");
        primaryStage.show();
    }

    private void showLuggageInputPage(Passenger passenger) {
        Label luggageLabel = new Label("Enter Luggage Information:");

        // Text fields for luggage information
        TextField lengthField = new TextField();
        lengthField.setPromptText("Length");
        TextField widthField = new TextField();
        widthField.setPromptText("Width");
        TextField heightField = new TextField();
        heightField.setPromptText("Height");
        TextField weightField = new TextField();
        weightField.setPromptText("Weight");

        // Button to save luggage information
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            try {
                saveLuggageInfo(passenger, lengthField.getText(), widthField.getText(), heightField.getText(), weightField.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        VBox inputBox = new VBox(luggageLabel, lengthField, widthField, heightField, weightField, saveButton);

        // Button to return to previous scene
        Button returnButton = new Button("Return");
        returnButton.setOnAction(event -> showQueryResult(passenger));

        VBox root = new VBox(inputBox, returnButton);
        Scene scene = new Scene(root, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Luggage Information");
        primaryStage.show();
    }

    private void saveLuggageInfo(Passenger passenger, String length, String width, String height, String weight) throws IOException {
        try {
            System.out.println("Length: " + length);
            System.out.println("Width: " + width);
            System.out.println("Height: " + height);
            System.out.println("Weight: " + weight);

            double luggageLength = Double.parseDouble(length);
            double luggageWidth = Double.parseDouble(width);
            double luggageHeight = Double.parseDouble(height);
            double luggageWeight = Double.parseDouble(weight);

            // Calculate luggage volume
            double luggageVolume = luggageLength * luggageWidth * luggageHeight;

            System.out.println("Luggage Volume: " + luggageVolume);

            List<Flight> flightList = FlightDataHandler.flightListGetter();

            System.out.println(flightList);

            // Find the flight associated with the passenger
            currentFlight = flightList.stream()
                    .filter(flight -> flight.getFlightCode().equals(passenger.getFlightCode()))
                    .findFirst()
                    .orElse(null);

            System.out.println("Current Flight: " + currentFlight);

            // Calculate max luggage weight for the flight
            double maxLuggageWeight = currentFlight.getMaxLuggageWeight() / currentFlight.getMaxCapacity();

            System.out.println("Max Luggage Weight: " + maxLuggageWeight);

            // Check if luggage is excess
            double excessWeight = Math.max(0, luggageWeight - maxLuggageWeight);

            System.out.println("Excess Weight: " + excessWeight);

            // Calculate excess fee
            double excessFee = Math.round((excessWeight * currentFlight.getExcessFeePerUnitWeight()) * 100.0) / 100.0;

            System.out.println("Excess Fee: " + excessFee);

            passenger.checkedInProperty().set(true);

            // Show luggage and excess fee info
            showLuggageAndFeeInfo(passenger, luggageLength, luggageWidth, luggageHeight, luggageWeight, luggageVolume, excessFee);
        } catch (NumberFormatException e) {
            showErrorAlert("Invalid luggage information. Please enter valid numeric values.");
        }

    }

    private void showLuggageAndFeeInfo(Passenger passenger, double length, double width, double height, double weight, double volume, double excessFee) {
        Label passengerLabel = new Label("Passenger Information:");
        Label nameLabel = new Label("Name: " + passenger.getName());
        Label bookingRefLabel = new Label("Booking Reference: " + passenger.getBookingReferenceCode());
        Label flightCodeLabel = new Label("Flight Code: " + passenger.getFlightCode());
        Label checkedInLabel = new Label("Checked In: " + (passenger.isCheckedIn() ? "Yes" : "No"));

        Label luggageLabel = new Label("Luggage Information:");
        Label lengthLabel = new Label("Length: " + length);
        Label widthLabel = new Label("Width: " + width);
        Label heightLabel = new Label("Height: " + height);
        Label weightLabel = new Label("Weight: " + weight);
        Label volumeLabel = new Label("Volume: " + volume);
        Label excessFeeLabel = new Label("Excess Fee: " + excessFee);

        VBox passengerInfoGroup = new VBox(passengerLabel, nameLabel, bookingRefLabel, flightCodeLabel, checkedInLabel);
        passengerInfoGroup.setSpacing(10);

        VBox luggageInfoGroup = new VBox(luggageLabel, lengthLabel, widthLabel, heightLabel, weightLabel, volumeLabel, excessFeeLabel);
        luggageInfoGroup.setSpacing(10);

        HBox hbox = new HBox(passengerInfoGroup, luggageInfoGroup);
        hbox.setSpacing(50);

        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(event -> {
            savePassengerAndLuggage(passenger, length, width, height, weight, volume, excessFee);
            showSaveSuccessAlert();
        });

        // Button to return to previous scene
        Button returnButton = new Button("Return");
        returnButton.setOnAction(event -> showLuggageInputPage(passenger));

        // Button to return to main menu
        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.setOnAction(event -> showMainScene());

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> {
            primaryStage.close();
            launchFlightReportGeneratorUI();
        });

        // Setting the positions of the buttons
        HBox bottomButtons = new HBox(mainMenuButton, exitButton);
        bottomButtons.setSpacing(20);
        bottomButtons.setAlignment(Pos.CENTER);
        VBox root = new VBox(hbox, confirmButton, returnButton, bottomButtons);
        root.setSpacing(20);
        root.setPadding(new Insets(20)); // Add padding to the root VBox
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Passenger and Luggage Information");
        primaryStage.show();
    }




    private void savePassengerAndLuggage(Passenger passenger, double length, double width, double height, double weight, double volume, double excessFee) {
        try {
            File file = new File("Datasets/CheckedInPassenger.csv");
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(passenger.getBookingReferenceCode() + "," + passenger.getName() + "," + passenger.getFlightCode() + ","
                    + passenger.isCheckedIn() + "," + length + "," + width + "," + height + "," + weight + "," + volume + "," + excessFee);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showSaveSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Save Success");
        alert.setHeaderText(null);
        alert.setContentText("Passenger and luggage information saved successfully. Check-In Successfully.");
        alert.showAndWait();
    }

    private void showInputErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText("Invalid input. Please enter valid values.");
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void launchFlightReportGeneratorUI() {
        FlightReportGeneratorUI flightReportGeneratorUI = new FlightReportGeneratorUI();
        Stage stage = new Stage();
        flightReportGeneratorUI.start(stage);
    }


    @Override
    public void stop() {
    }

    public static void main(String[] args) {
        launch(args);
    }
}
