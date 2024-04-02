package com.cw_2;

public class SimulationController {
    public static void main(String[] args) {
        QueueSystem queueSystem = new QueueSystem();
        DeskRunnable checkInDesk = new DeskRunnable(queueSystem);
        Thread deskThread = new Thread(checkInDesk);
        deskThread.start();

        for (int i = 1; i <=9 ; i++) {
            Passenger passenger = new Passenger("Passenger" + i, "Flight100", 15.0 + i, 1.0);
            queueSystem.addPassengerToQueue(passenger);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(10000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        checkInDesk.setOpen(false); 
        deskThread.interrupt(); 
    }
}
