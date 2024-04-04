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



