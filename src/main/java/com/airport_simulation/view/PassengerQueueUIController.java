package src.main.java.com.airport_simulation.view;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import src.main.java.com.airport_simulation.data_structure.Passenger;

import java.util.Queue;

public class PassengerQueueUIController {
    @FXML
    private ListView<String> passengerListView;

    public void updatePassengerList(Queue<Passenger> queue) {
        passengerListView.getItems().clear();
        for (Passenger passenger : queue) {
            passengerListView.getItems().add(passenger.getName());
        }
    }
}
