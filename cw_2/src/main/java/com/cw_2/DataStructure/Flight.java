package com.cw_2.DataStructure;

public class Flight {
    private String flightCode;
    private String airport;
    private String airline;
    private double excessFeePerUnitWeight;
    private int maxCapacity;
    private double maxLuggageWeight;
    private double maxLuggageVolume;

    public Flight(String flightCode, String airport, String airline, double excessFeePerUnitWeight, int maxCapacity, double maxLuggageWeight, double maxLuggageVolume) {
        this.flightCode = flightCode;
        this.airport = airport;
        this.airline = airline;
        this.excessFeePerUnitWeight = excessFeePerUnitWeight;
        this.maxCapacity = maxCapacity;
        this.maxLuggageWeight = maxLuggageWeight;
        this.maxLuggageVolume = maxLuggageVolume;
    }

    // Getters and setters
    // You can generate these using your IDE or write them manually
    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public double getExcessFeePerUnitWeight() {
        return excessFeePerUnitWeight;
    }

    public void setExcessFeePerUnitWeight(double excessFeePerUnitWeight) {
        this.excessFeePerUnitWeight = excessFeePerUnitWeight;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public double getMaxLuggageWeight() {
        return maxLuggageWeight;
    }

    public void setMaxLuggageWeight(double maxLuggageWeight) {
        this.maxLuggageWeight = maxLuggageWeight;
    }

    public double getMaxLuggageVolume() {
        return maxLuggageVolume;
    }

    public void setMaxLuggageVolume(double maxLuggageVolume) {
        this.maxLuggageVolume = maxLuggageVolume;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightCode='" + flightCode + '\'' +
                ", airport='" + airport + '\'' +
                ", airline='" + airline + '\'' +
                ", excessFeePerUnitWeight=" + excessFeePerUnitWeight +
                ", maxCapacity=" + maxCapacity +
                ", maxLuggageWeight=" + maxLuggageWeight +
                ", maxLuggageVolume=" + maxLuggageVolume +
                '}';
    }
}