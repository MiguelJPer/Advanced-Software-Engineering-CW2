package com.cw_2.DataStructure;

public class PassengerAtCounter {
    private Passenger passenger; // the shared item; a number
    // initialise number to 0
    private boolean empty;
    private boolean done;

    public PassengerAtCounter() {
        passenger = null;
        empty = true;
        done = false;
    }

    public void setDone() {
        done = true;
    }

    public boolean getDone() {
        return done;
    }

    // called by consumer, gets and prints the value of n
    public synchronized Passenger get() {
        while (empty) { // when no number available
            try {
                wait();
            } // consumer enters Waiting state
            catch (InterruptedException e) {
            }
            // it will stay here until notified
        }
        System.out.println("Got: " + passenger);
        empty = true; // consumer has consumed the number
        notifyAll(); // so wake up the producer
        return passenger;
    }

    // by producer, puts and prints the value of n
    public synchronized void put(Passenger newPassenger) {
        while (!empty) { // when slot not available
            try {
                wait();
            } // producer enters Waiting state
            catch (InterruptedException e) {
            }
        }
        System.out.println("Put: " + passenger);
        empty = false; // producer has produced a new number
        notifyAll(); // so wake up the consumer
        this.passenger = passenger;
    }
}
