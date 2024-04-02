package com.cw_2;

public class Passenger {
    private String id;
    private String flightId;
    private double baggageWeight; // 行李重量
    private double baggageVolume; // 行李体积
    private boolean isCheckedIn;
    private double excessFee; // 超重费用
    private boolean isAssignedToFlight = false; // 乘客是否已分配到航班

    public Passenger(String id, String flightId, double baggageWeight, double baggageVolume) {
        this.id = id;
        this.flightId = flightId;
        this.baggageWeight = baggageWeight;
        this.baggageVolume = baggageVolume;
        this.isCheckedIn = false;
        this.excessFee = 0.0;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getFlightId() { return flightId; }
    public void setFlightId(String flightId) { this.flightId = flightId; }
    public double getBaggageWeight() { return baggageWeight; }
    public void setBaggageWeight(double baggageWeight) { this.baggageWeight = baggageWeight; }
    public double getBaggageVolume() { return baggageVolume; }
    public void setBaggageVolume(double baggageVolume) { this.baggageVolume = baggageVolume; }
    public boolean isCheckedIn() { return isCheckedIn; }
    public void setCheckedIn(boolean checkedIn) { this.isCheckedIn = checkedIn; }
    public double getExcessFee() { return excessFee; }
    public void setExcessFee(double excessFee) { this.excessFee = excessFee; }
    public boolean isAssignedToFlight() { return isAssignedToFlight; }
    public void setAssignedToFlight(boolean isAssignedToFlight) { this.isAssignedToFlight = isAssignedToFlight; }
}
