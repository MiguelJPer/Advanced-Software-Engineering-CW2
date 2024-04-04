//package src.test.airport_simulation;
//
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.scene.Scene;
//import javafx.scene.control.ListView;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import src.main.java.com.airport_simulation.model.PassengerQueue;
//
//import java.util.List;
//
//public class PassengerQueueUITest extends Application {
//    @Override
//    public void start(Stage primaryStage) {
//        VBox root = new VBox();
//        ListView<String> listView = new ListView<>();
//        root.getChildren().add(listView);
//
//        Scene scene = new Scene(root, 300, 250);
//        primaryStage.setTitle("Passenger Queue");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//
//        // 假设PassengerQueue已经正确实现了Runnable接口
//        PassengerQueue passengerQueue = new PassengerQueue();
//        Thread queueThread = new Thread(passengerQueue); // 将PassengerQueue封装到线程中
//        queueThread.start(); // 启动线程
//
//        // 启动一个线程，负责更新ListView
//        new Thread(() -> {
//            while (true) {
//                List<String> passengerNames = passengerQueue.getPassengerNames(); // 获取乘客名称列表
//                Platform.runLater(() -> {
//                    listView.getItems().setAll(passengerNames); // 更新ListView
//                });
//                try {
//                    Thread.sleep(1000); // 每秒刷新一次
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
