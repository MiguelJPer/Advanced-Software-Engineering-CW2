/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package f21as.flightinfo;

/**
 *
 * @author pares
 */

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FlightDetailsWindow {

    public static void display(String flightCode, Flight flight) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Flight Details: " + flightCode);
        window.setMinWidth(250);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Add flight details labels
        Label flightCodeLabel = new Label("Flight Code:");
        GridPane.setConstraints(flightCodeLabel, 0, 0);
        Label flightCodeValueLabel = new Label(flight.getFlightCode());
        GridPane.setConstraints(flightCodeValueLabel, 1, 0);
        
        Label flightCodeName = new Label("Flight Name:");
        GridPane.setConstraints(flightCodeName, 0, 1);
        Label flightNameValueLabel = new Label(flight.getAirlineName());
        GridPane.setConstraints(flightNameValueLabel, 1, 1);

        Label holdFullnessLabel = new Label("Hold Fullness:");
        GridPane.setConstraints(holdFullnessLabel, 0, 2);
        double holdFullnessPercentage = (flight.getBaggageVolume() / flight.getHoldVolume());
        Label holdFullnessValueLabel = new Label(String.format("%.2f%%", holdFullnessPercentage));
        GridPane.setConstraints(holdFullnessValueLabel, 1, 2);

        Label checkedInCountLabel = new Label("Checked-in Count:");
        GridPane.setConstraints(checkedInCountLabel, 0, 3);
        Label checkedInCountValueLabel = new Label(String.format("%d of %d", flight.getCheckedInCount(), flight.getCapacity()));
        GridPane.setConstraints(checkedInCountValueLabel, 1, 3);

        grid.getChildren().addAll(flightCodeLabel, flightCodeValueLabel, flightCodeName, flightNameValueLabel, holdFullnessLabel, holdFullnessValueLabel,
                checkedInCountLabel, checkedInCountValueLabel);

        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.showAndWait();
    }
}
