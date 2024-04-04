package src.main.java.com.airport_simulation.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import src.main.java.com.airport_simulation.data_structure.Passenger;
import src.main.java.com.airport_simulation.model.CheckInDesk;
import src.main.java.com.airport_simulation.model.PassengerQueue;

public class AirportSimulationController {

    @FXML
    private ListView<String> checkInDeskListView; // 登机口信息列表视图

    @FXML
    private ListView<String> flightInfoListView; // 航班信息列表视图

    @FXML
    private ListView<String> passengerQueueListView; // UI组件，用于显示乘客队列信息

    private ObservableList<String> checkInDeskMessages = FXCollections.observableArrayList();
    private ObservableList<String> flightInfoForDisplay = FXCollections.observableArrayList();
    private ObservableList<String> passengerQueueInfo = FXCollections.observableArrayList();

    public void initialize() {
        checkInDeskListView.setItems(checkInDeskMessages);
        flightInfoListView.setItems(flightInfoForDisplay);
        passengerQueueListView.setItems(passengerQueueInfo);
        // 模拟启动逻辑和线程启动代码，在这里简化展示
        startSimulation();
    }

    private void startSimulation() {
        ObservableList<Passenger> passengerList = FXCollections.observableArrayList();
        PassengerQueue passengerQueue = new PassengerQueue(passengerList);

        passengerQueue.setOnNewPassenger(passenger -> {
            String passengerInfo = formatPassengerInfo(passenger);
            Platform.runLater(() -> checkInDeskMessages.add(passengerInfo));
        });

        for (int i = 0; i < 2; i++) {
            CheckInDesk checkInDesk = new CheckInDesk(passengerList);
            checkInDesk.setOnPassengerProcessed(this::updateUIWithPassengerInfo);
            checkInDesk.setOnFlightUpdate(flightInfo -> Platform.runLater(() -> flightInfoForDisplay.add(flightInfo)));

            new Thread(checkInDesk, "CheckInDesk-" + i).start();
        }
    }

    // 格式化乘客信息的方法
    private String formatPassengerInfo(Passenger passenger) {
        // 格式化乘客信息，例如姓名和航班号
        return passenger.getName() + " - " + passenger.getFlightCode();
    }

    // 更新UI显示的乘客信息
    public void updateUIWithPassengerInfo(String message) {
        Platform.runLater(() -> checkInDeskMessages.add(message));
    }

    private void updateUIWithFlightInfo(String flightInfo) {
        Platform.runLater(() -> flightInfoForDisplay.add(flightInfo));
    }

    // 绑定登机口信息到UI
    public void bindCheckInDesks(ObservableList<String> messages) {
        checkInDeskListView.setItems(messages);
    }

    // 绑定航班信息到UI
    public void bindFlightInfo(ObservableList<String> flightInfo) {
        flightInfoListView.setItems(flightInfo);
    }

    // 方法：绑定PassengerQueue信息到UI
    public void bindPassengerQueue(ObservableList<Passenger> passengerQueue) {
        passengerQueue.forEach(passenger -> {
            String passengerInfo = formatPassengerInfo(passenger); // 假设这个方法将Passenger对象格式化为字符串
            passengerQueueInfo.add(passengerInfo); // 将格式化后的乘客信息添加到UI列表中
        });

        // 监听PassengerQueue的变化，每当有新乘客加入时更新UI
        // 注意：这里的实现依赖于PassengerQueue中有方法或机制可以通知外部乘客加入事件，具体实现可能需要调整
    }

    // 根据需要添加其他必要的方法和逻辑
}
