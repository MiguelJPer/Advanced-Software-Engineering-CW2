package src.main.java.com.airport_simulation.util;

public class Airline {
    private String name;
    private int freeLuggageAllowance;
    private double excessLuggageCharge;

    public Airline(String name, int freeLuggageAllowance, double excessLuggageCharge) {
        this.name = name;
        this.freeLuggageAllowance = freeLuggageAllowance;
        this.excessLuggageCharge = excessLuggageCharge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFreeLuggageAllowance() {
        return freeLuggageAllowance;
    }

    public void setFreeLuggageAllowance(int freeLuggageAllowance) {
        this.freeLuggageAllowance = freeLuggageAllowance;
    }

    public double getExcessLuggageCharge() {
        return excessLuggageCharge;
    }

    public void setExcessLuggageCharge(double excessLuggageCharge) {
        this.excessLuggageCharge = excessLuggageCharge;
    }
}
