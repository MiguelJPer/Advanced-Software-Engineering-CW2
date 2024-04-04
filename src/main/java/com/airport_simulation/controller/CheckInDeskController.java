package src.main.java.com.airport_simulation.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class CheckInDeskController {

    @FXML
    private ListView<String> checkInDeskInfoListView; // ListView for displaying information about the check-in desk operations.

    @FXML
    private ListView<String> flightInfoListView; // ListView for displaying flight information.

    // Updates the check-in desk information displayed in the UI.
    // This method is called to add new messages to the check-in desk information ListView.
    public void updateCheckInDeskInfo(String message) {
        // Ensures that the update to the ListView is performed on the JavaFX Application Thread.
        Platform.runLater(() -> checkInDeskInfoListView.getItems().add(message));
    }

    // Updates the flight information displayed in the UI.
    // This method is called to replace the existing flight information in the ListView with new data.
    public void updateFlightInfoList(ObservableList<String> flightInfo) {
        // Ensures that the update to the ListView is performed on the JavaFX Application Thread.
        Platform.runLater(() -> flightInfoListView.setItems(flightInfo));
    }
}
