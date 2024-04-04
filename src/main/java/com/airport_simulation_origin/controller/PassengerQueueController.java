//package src.main.java.com.airport_simulation_origin.controller;
//
//import javafx.application.Platform;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.scene.control.Label;
//import javafx.scene.control.ListView;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.layout.VBox;
//import src.main.java.com.airport_simulation_origin.data_structure.Passenger;
//import src.main.java.com.airport_simulation_origin.model.PassengerQueue;
//
//import java.util.List;
//
//
//public class PassengerQueueController {
//    @FXML
//    private ListView<String> passengerListView;
//
//    private ObservableList<String> passengerList = FXCollections.observableArrayList();
//
//    // 移除了final修饰符
//    private PassengerQueue passengerQueue;
//
//    public void setPassengerQueue(PassengerQueue passengerQueue) {
//        this.passengerQueue = passengerQueue;
//        updatePassengerList(); // 现在可以调用updatePassengerList来更新UI
//    }
//
//    @FXML
//    public void initialize() {
//        passengerListView.setItems(passengerList);
//    }
//
//    private void updatePassengerList() {
//        new Thread(() -> {
//            while (true) {
//                List<String> passengerInfo = passengerQueue.getQueueSnapshot().stream()
//                        .map(passenger -> String.format("%s - Flight: %s, Baggage Weight: %.2f, Dimensions: %s, Fee: %.2f",
//                                passenger.getName(), passenger.getFlightCode(), passenger.getBaggageWeight(),
//                                formatDimensions(passenger.getBaggageDimensions()), passenger.getBaggageFee()))
//                        .collect(Collectors.toList());
//
//                Platform.runLater(() -> passengerList.setAll(passengerInfo));
//
//                try {
//                    Thread.sleep(1000); // 每秒刷新一次
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
//
//    private String formatDimensions(double[] dimensions) {
//        return String.format("L: %.2f, W: %.2f, H: %.2f", dimensions[0], dimensions[1], dimensions[2]);
//    }
//}
//
