package src;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.DataStructure.Passenger;

import java.io.FileNotFoundException;
import java.util.List;

public class PassengerDataUI extends Application {
    private List<Passenger> passengerList;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button showButton = new Button("Show Data");
        showButton.setOnAction(event -> {
            try {
                passengerList = PassengerDataHandler.passengerListGetter();
                showPassengerData(primaryStage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        VBox root = new VBox(showButton);
        Scene scene = new Scene(root, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Passenger Data");
        primaryStage.show();
    }

    private void showPassengerData(Stage primaryStage) {
        TableView<Passenger> tableView = new TableView<>();

        TableColumn<Passenger, String> bookingRefCol = new TableColumn<>("Booking Reference");
        bookingRefCol.setCellValueFactory(cellData -> cellData.getValue().bookingReferenceCodeProperty());

        TableColumn<Passenger, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        TableColumn<Passenger, String> flightCodeCol = new TableColumn<>("Flight Code");
        flightCodeCol.setCellValueFactory(cellData -> cellData.getValue().flightCodeProperty());

        TableColumn<Passenger, Boolean> isCheckedCol = new TableColumn<>("Checked In");
        isCheckedCol.setCellValueFactory(cellData -> cellData.getValue().checkedInProperty());

        tableView.getColumns().addAll(bookingRefCol, nameCol, flightCodeCol, isCheckedCol);

        ObservableList<Passenger> data = FXCollections.observableArrayList(passengerList);
        tableView.setItems(data);

        VBox root = new VBox(tableView);
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Passenger List");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Scene createScene(Stage primaryStage) {
        return null;
    }
}
