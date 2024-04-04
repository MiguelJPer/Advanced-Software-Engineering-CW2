package src.main.java.com.airport_simulation.controller;


import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class CheckInDeskController {

    @FXML
    private ListView<String> checkInDeskInfoListView;

    @FXML
    private ListView<String> flightInfoListView;

    public void updateCheckInDeskInfo(String message) {
        Platform.runLater(() -> checkInDeskInfoListView.getItems().add(message));
    }

    public void updateFlightInfoList(ObservableList<String> flightInfo) {
        Platform.runLater(() -> flightInfoListView.setItems(flightInfo));
    }
}

