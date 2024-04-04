package src.main.java.com.airport_simulation.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import src.main.java.com.airport_simulation.data_structure.Passenger;
import src.main.java.com.airport_simulation.model.PassengerQueue;

import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PassengerQueueController {
    @FXML private VBox mainContainer;
    @FXML private Label queueCountLabel;
    @FXML private TableView<Passenger> passengerTable;
    @FXML private TableColumn<Passenger, String> flightCodeColumn;
    @FXML private TableColumn<Passenger, String> nameColumn;
    @FXML private TableColumn<Passenger, Double> weightColumn;
    @FXML private TableColumn<Passenger, String> dimensionsColumn;

    private PassengerQueue passengerQueue; // 应该被传入的PassengerQueue对象的引用

    @FXML
    public void initialize() {
        // 将Passenger属性绑定到表格列
        flightCodeColumn.setCellValueFactory(cellData -> cellData.getValue().flightCodeProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        weightColumn.setCellValueFactory(cellData -> cellData.getValue().baggageWeightProperty().asObject());
        dimensionsColumn.setCellValueFactory(cellData -> cellData.getValue().baggageDimensionsProperty());

        // 启动一个后台线程，定期更新UI
        Thread updateThread = new Thread(() -> {
            while (true) {
                Platform.runLater(() -> {
                    passengerTable.setItems(passengerQueue.getPassengerList());
                    queueCountLabel.setText("There are currently " + passengerQueue.getQueueSize() + " people waiting in the queue:");
                });
                try {
                    Thread.sleep(1000); // 每秒更新一次UI
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        updateThread.setDaemon(true);
        updateThread.start();
    }

    // 用于从外部设置PassengerQueue对象的方法
    public void setPassengerQueue(PassengerQueue queue) {
        this.passengerQueue = queue;
    }
}



