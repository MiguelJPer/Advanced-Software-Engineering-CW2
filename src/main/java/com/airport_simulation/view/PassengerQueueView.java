package src.main.java.com.airport_simulation.view;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import src.main.java.com.airport_simulation.controller.PassengerQueueController;
import src.main.java.com.airport_simulation.model.PassengerQueue;

public class PassengerQueueView extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/main/resources/com/airport_simulation/view/PassengerQueueView.fxml"));
        Parent root = loader.load();

        PassengerQueueController controller = loader.getController();
        // 创建PassengerQueue对象，并将其传递给Controller
        PassengerQueue queue = new PassengerQueue();
        controller.setPassengerQueue(queue);

        primaryStage.setTitle("Airport Simulation");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        // 启动PassengerQueue线程（如果PassengerQueue实现了Runnable接口）
        new Thread(queue).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

//public class PassengerQueueView extends Application {
//
//    @Override
//    public void start(Stage primaryStage) throws IOException {
//        // 加载FXML文件
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/main/resources/com/airport_simulation/view/PassengerQueueView.fxml"));
//        Parent root = loader.load();
//
//        // 获取Controller实例
//        PassengerQueueController controller = loader.getController();
//
//        // 在这里启动PassengerQueue线程
//        PassengerQueue passengerQueue = new PassengerQueue();
//        controller.setPassengerQueue(passengerQueue); // 假设我们为Controller添加了这个方法
//
//        Thread queueThread = new Thread(passengerQueue);
//        queueThread.setDaemon(true); // 设置为守护线程，确保当应用退出时线程也会停止
//        queueThread.start();
//
//        primaryStage.setTitle("Passenger Queue Display");
//        primaryStage.setScene(new Scene(root, 400, 600)); // 设置场景大小
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}



