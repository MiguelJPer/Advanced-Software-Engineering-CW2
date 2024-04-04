package src.main.java.com.airport_simulation.data_structure;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Passenger {
    private final SimpleStringProperty flightCode;
    private final SimpleStringProperty name;
    private final SimpleDoubleProperty baggageWeight;
    private final SimpleStringProperty baggageDimensions;

    public Passenger(String flightCode, String name, double baggageWeight, double[] baggageDimensions) {
        this.flightCode = new SimpleStringProperty(flightCode);
        this.name = new SimpleStringProperty(name);
        this.baggageWeight = new SimpleDoubleProperty(baggageWeight);
        this.baggageDimensions = new SimpleStringProperty(
                String.format("%fx%fx%f", baggageDimensions[0], baggageDimensions[1], baggageDimensions[2])
        );
    }

    // Getters for the properties
    public SimpleStringProperty flightCodeProperty() {
        return flightCode;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleDoubleProperty baggageWeightProperty() {
        return baggageWeight;
    }

    public SimpleStringProperty baggageDimensionsProperty() {
        return baggageDimensions;
    }


    // Regular getters
    public String getFlightCode() {
        return flightCode.get();
    }

    public String getName() {
        return name.get();
    }

    public double getBaggageWeight() {
        return baggageWeight.get();
    }

    public String getBaggageDimensions() {
        return baggageDimensions.get();
    }


    // Setters
    public void setFlightCode(String flightCode) {
        this.flightCode.set(flightCode);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setBaggageWeight(double baggageWeight) {
        this.baggageWeight.set(baggageWeight);
    }

    public void setBaggageDimensions(double[] baggageDimensions) {
        this.baggageDimensions.set(
                String.format("%fx%fx%f", baggageDimensions[0], baggageDimensions[1], baggageDimensions[2])
        );
    }
}
