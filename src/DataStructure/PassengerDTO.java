package src.DataStructure;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PassengerDTO {
    private final StringProperty bookingReferenceCode;
    private final StringProperty name;
    private final StringProperty flightCode;
    private final BooleanProperty checkedIn;

    public PassengerDTO(String bookingReferenceCode, String name, String flightCode, boolean checkedIn) {
        this.bookingReferenceCode = new SimpleStringProperty(bookingReferenceCode);
        this.name = new SimpleStringProperty(name);
        this.flightCode = new SimpleStringProperty(flightCode);
        this.checkedIn = new SimpleBooleanProperty(checkedIn);
    }

    // Constructor to accept Passenger object
    public PassengerDTO(Passenger passenger) {
        this.bookingReferenceCode = new SimpleStringProperty(passenger.getBookingReferenceCode());
        this.name = new SimpleStringProperty(passenger.getName());
        this.flightCode = new SimpleStringProperty(passenger.getFlightCode());
        this.checkedIn = new SimpleBooleanProperty(passenger.isCheckedIn());
    }

    // Getter methods for JavaFX Properties
    public StringProperty bookingReferenceCodeProperty() {
        return bookingReferenceCode;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty flightCodeProperty() {
        return flightCode;
    }

    public BooleanProperty checkedInProperty() {
        return checkedIn;
    }

    // Other methods
}
