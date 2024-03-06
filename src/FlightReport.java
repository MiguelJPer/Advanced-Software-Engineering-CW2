package src;

class FlightReport {
    private int passengerCount;
    private double totalLuggageWeight;
    private double totalLuggageVolume;
    private double totalExcessFee;
    private boolean exceededCapacity;

    public FlightReport() {
        this.passengerCount = 0;
        this.totalLuggageWeight = 0.0;
        this.totalLuggageVolume = 0.0;
        this.totalExcessFee = 0.0;
        this.exceededCapacity = false;
    }

    public void incrementPassengerCount() {
        this.passengerCount++;
    }

    public void addToTotalLuggageWeight(double weight) {
        this.totalLuggageWeight += weight;
    }

    public void addToTotalLuggageVolume(double volume) {
        this.totalLuggageVolume += volume;
    }

    public void addToTotalExcessFee(double fee) {
        this.totalExcessFee += fee;
    }

    public void setExceededCapacity(boolean exceededCapacity) {
        this.exceededCapacity = exceededCapacity;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public double getTotalLuggageWeight() {
        return totalLuggageWeight;
    }

    public double getTotalLuggageVolume() {
        return totalLuggageVolume;
    }

    public double getTotalExcessFee() {
        return totalExcessFee;
    }

    public boolean isExceededCapacity() {
        return exceededCapacity;
    }
}