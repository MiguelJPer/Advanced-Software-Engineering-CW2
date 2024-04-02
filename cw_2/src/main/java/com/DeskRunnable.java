package com;

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
        Passenger passenger = passengerAtCounter.get();
        while (desk.IsEmpty() == false) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        desk.NewPassenger(passenger);
        // Set desk UI to desk.CheckInDetails();
        desk.CheckInPassenger();
        boardingPassenger.put(passenger);
    }
}
