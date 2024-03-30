/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package f21as.flightinfo;

/**
 *
 * @author pares
 */
public class Flight {
    private String flightCode;
    private int capacity;
    private double holdVolume;
    private int checkedInCount = 0;
    private double baggageVolume = 0;
    private final String airlineName;

    public Flight(String flightCode, String airlineName, int capacity, double holdVolume) {
        this.flightCode = flightCode;
        this.airlineName = airlineName;
        this.capacity = capacity;
        this.holdVolume = holdVolume;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getHoldVolume() {
        return holdVolume;
    }

    public int getCheckedInCount() {
        return checkedInCount;
    }

    public double getBaggageVolume() {
        return baggageVolume;
    }

    public void incrementCheckedInCount() {
        this.checkedInCount++;
    }

    public void addBaggageVolume(double volume) {
        this.baggageVolume += volume;
    }
}
