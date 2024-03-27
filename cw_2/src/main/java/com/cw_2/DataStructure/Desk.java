package com.cw_2.DataStructure;

public class Desk {

    Passenger passengerAtDesk;
    Flight passengerFlight;
    String detailMessage;
    boolean empty;

    public Desk(Passenger currentPassenger) {
        this.passengerAtDesk = currentPassenger;

        // Find passenger's flight from list of active flights
        // EG: this.passengerFlight = GetFlight(currentPassenger.getFlightCode());
    }

    public void NewPassenger(Passenger newPassenger) {
        this.passengerAtDesk = newPassenger;
    }

    public String GetCheckInDetails() {
        int baggageFee = // Get baggage fee based on parameters from flight
                detailMessage = new String(passengerAtDesk.getName() + " is checking in luggage. Weight: "
                        + passengerAtDesk.luggage + "/n Baggage excess fee: " + StringbaggageFee);
        return detailMessage;
    }

    public void CheckInPassenger() {
        // Check in the passenger in the correct flight
    }

    public boolean IsEmpty() {
        return empty;
    }
}
