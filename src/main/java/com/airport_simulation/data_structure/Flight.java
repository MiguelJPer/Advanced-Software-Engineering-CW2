package src.main.java.com.airport_simulation.data_structure;

import java.util.ArrayList;
import java.util.List;

public class Flight {
    private String flightCode;
    private List<Passenger> passengers;
    private double totalBaggageWeight;

    public Flight(String flightCode) {
        this.flightCode = flightCode;
        this.passengers = new ArrayList<>();
        this.totalBaggageWeight = 0.0;
    }

    // 添加乘客到航班
    public synchronized void addPassenger(Passenger passenger) {
        passengers.add(passenger);
        // 假设每个乘客只有一件行李
        totalBaggageWeight += passenger.getBaggageWeight();
        System.out.println("Passenger " + passenger.getName() + " added to flight " + flightCode);
    }

    // 获取航班号
    public String getFlightCode() {
        return flightCode;
    }

    // 获取航班上的乘客列表
    public List<Passenger> getPassengers() {
        return passengers;
    }

    // 获取航班上的总行李重量
    public double getTotalBaggageWeight() {
        return totalBaggageWeight;
    }

    // 这里可以添加更多的方法，比如获取已登机乘客数等
}
