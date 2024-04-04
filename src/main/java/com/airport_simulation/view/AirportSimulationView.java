package src.main.java.com.airport_simulation.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import src.main.java.com.airport_simulation.controller.AirportSimulationController;
import src.main.java.com.airport_simulation.model.CheckInDesk;
import src.main.java.com.airport_simulation.model.PassengerQueue;

public class AirportSimulationView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/main/resources/com/airport_simulation/view/AirportSimulationView.fxml"));
        Parent root = loader.load();

        // 获取控制器实例
        AirportSimulationController controller = loader.getController();

        // 创建 PassengerQueue
        ObservableList<PassengerQueue> passengerQueues = FXCollections.observableArrayList();
        PassengerQueue passengerQueue = new PassengerQueue(); // 这里假设PassengerQueue已经适配了ObservableList
        Thread passengerQueueThread = new Thread(passengerQueue);
        passengerQueueThread.setDaemon(true);
        passengerQueueThread.start();

        // 创建 CheckInDesk
        ObservableList<CheckInDesk> desks = FXCollections.observableArrayList();
        final int numberOfDesks = 2;
        for (int i = 0; i < numberOfDesks; i++) {
            CheckInDesk checkInDesk = new CheckInDesk(passengerQueue.getPassengerList()); // 适配ObservableList
            Thread deskThread = new Thread(checkInDesk, "CheckInDesk-" + i);
            deskThread.setDaemon(true);
            deskThread.start();
            desks.add(checkInDesk);

            // 更新UI
            checkInDesk.setOnPassengerProcessed(controller::updateUIWithPassengerInfo); // 假设这个方法在Controller中定义
        }

        // 将数据绑定到控制器
        controller.bindCheckInDesks(desks);
        controller.bindPassengerQueue(passengerQueue.getPassengerList());

        primaryStage.setTitle("Airport Simulation");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
