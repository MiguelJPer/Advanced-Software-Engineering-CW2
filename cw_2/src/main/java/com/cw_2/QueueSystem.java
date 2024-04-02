package com.cw_2;

import java.util.LinkedList;
import java.util.Queue;

import com.cw_2.DataStructure.PassengerAtCounter;
import com.cw_2.DataStructure.Passenger;


public class QueueSystem implements Runnable {
    private final Queue<Passenger> passengerQueue;
    private PassengerAtCounter passengerAtCounter;

    public QueueSystem(PassengerAtCounter passengerAtCounter) {
        this.passengerAtCounter = passengerAtCounter;
        passengerQueue = new LinkedList<>();
    }

    public synchronized void addPassengerToQueue(Passenger passenger) {
        passengerQueue.add(passenger);
        System.out.println("Passenger added to the queue: " + passenger.getName());
    }

    public synchronized Passenger processNextPassenger() {
        if (!passengerQueue.isEmpty()) {
            return passengerQueue.poll();
        }
        return null;
    }

    public synchronized int getQueueSize() {
        return passengerQueue.size();
    }

    @Override
    public void run() {
        for (int i = 0; i < 8; i++) {
            try {Thread.sleep(50); }
            catch (InterruptedException e) {}
            passengerAtCounter.put(processNextPassenger()); // produce
            }
            passengerAtCounter.setDone();
    }
}