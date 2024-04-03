package src.main.java.com.airport_simulation.controller;

import src.main.java.com.airport_simulation.model.PassengerQueue;
import src.main.java.com.airport_simulation.data_structure.Passenger;

import java.util.LinkedList;
import java.util.Queue;

public class PassengerQueueController {
    private PassengerQueue passengerQueue;
    private Thread passengerQueueThread;

    public PassengerQueueController() {
        Queue<Passenger> queue = new LinkedList<>();
        this.passengerQueue = new PassengerQueue(queue);
        this.passengerQueueThread = new Thread(passengerQueue);
    }

    public void startSimulation() {
        passengerQueueThread.start();
    }

    public void stopSimulation() {
        passengerQueue.stopRunning();
        passengerQueueThread.interrupt();
    }

    public Queue<Passenger> getCurrentQueue() {
        return passengerQueue.getQueue();
    }
}
