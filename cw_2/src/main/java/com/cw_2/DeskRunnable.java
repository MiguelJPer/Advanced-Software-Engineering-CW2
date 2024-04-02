package com.cw_2;

import org.w3c.dom.css.Counter;

import com.cw_2.DataStructure.BoardingPassenger;
import com.cw_2.DataStructure.Desk;
import com.cw_2.DataStructure.Passenger;
import com.cw_2.DataStructure.PassengerAtCounter;

public class DeskRunnable implements Runnable {
    private PassengerAtCounter passengerAtCounter;
    private Desk desk;
    private BoardingPassenger boardingPassenger;

    public DeskRunnable(PassengerAtCounter passenger, Desk desk) {
        this.passengerAtCounter = passenger;
        this.desk = desk;
    }

    public void run() {
        while (!passengerAtCounter.getDone()) { // while producing
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            Passenger passenger = passengerAtCounter.get();
            desk.NewPassenger(passengerAtCounter.get());
            // Set desk UI to desk.CheckInDetails();
            desk.CheckInPassenger();
            boardingPassenger.put(passenger);
        }
    }
}
