package com.cw_2.DataStructure;

public class PassengerAtCounter {
    private Passenger passenger; // the shared item; a number
    // initialise number to 0

    public PassengerAtCounter() {
        passenger = null;
    }

    // called by consumer, gets and prints the value of n
    public synchronized Passenger get() {
        System.out.println("Got: " + passenger);
        return passenger;
    }

    // by producer, puts and prints the value of n
    public synchronized void put(Passenger newPassenger) {
        this.passenger = newPassenger;
        System.out.println("Put: " + newPassenger);
    }
}
