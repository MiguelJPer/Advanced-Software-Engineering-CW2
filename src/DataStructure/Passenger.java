package src.DataStructure;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Passenger {
    private final StringProperty bookingReferenceCode;
    private final StringProperty name;
    private final StringProperty flightCode;
    private final BooleanProperty checkedIn;

    public Passenger(String bookingReferenceCode, String name, String flightCode, boolean checkedIn) {
        this.bookingReferenceCode = new SimpleStringProperty(bookingReferenceCode);
        this.name = new SimpleStringProperty(name);
        this.flightCode = new SimpleStringProperty(flightCode);
        this.checkedIn = new SimpleBooleanProperty(checkedIn);
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

    // Getter methods for original data types
    public String getBookingReferenceCode() {
        return bookingReferenceCode.get();
    }

    public String getName() {
        return name.get();
    }

    public String getFlightCode() {
        return flightCode.get();
    }

    public boolean isCheckedIn() {
        return checkedIn.get();
    }
}
