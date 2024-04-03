package src.main.java.com.airport_simulation.data_structure;

public class Passenger {
    private String flightCode;
    private String name;
    private double baggageWeight;
    private double[] baggageDimensions; // Using an array to store the dimensions: length, width, height

    public Passenger(String flightCode, String name, double baggageWeight, double[] baggageDimensions) {
        this.flightCode = flightCode;
        this.name = name;
        this.baggageWeight = baggageWeight;
        this.baggageDimensions = baggageDimensions; // Assuming the array has 3 elements representing length, width, height
    }

    // Getters
    public String getFlightCode() {
        return flightCode;
    }

    public String getName() {
        return name;
    }

    public double getBaggageWeight() {
        return baggageWeight;
    }

    public double[] getBaggageDimensions() {
        return baggageDimensions;
    }

    // Setters are omitted for brevity
}
