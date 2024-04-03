package src.main.java.com.airport_simulation.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.main.java.com.airport_simulation.controller.PassengerQueueController;
import src.main.java.com.airport_simulation.data_structure.Passenger;

public class PassengerQueueUI extends Application {
    private PassengerQueueController controller = new PassengerQueueController();
    private ObservableList<String> passengerList = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        controller.startSimulation(); // 开始模拟

        ListView<String> listView = new ListView<>(passengerList);
        VBox vbox = new VBox(listView);
        Scene scene = new Scene(vbox, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        // 定期更新UI
        new Thread(() -> {
            while (true) {
                updatePassengerList();
                try {
                    Thread.sleep(1000); // 每秒更新一次
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void updatePassengerList() {
        javafx.application.Platform.runLater(() -> {
            passengerList.clear();
            for (Passenger passenger : controller.getCurrentQueue()) {
                passengerList.add(passenger.getName() + " - " + passenger.getFlightCode());
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
