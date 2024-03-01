import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PassengerDataHandler {
    public static void main(String[] args) throws Exception {
        int scannerIndex = 0;
        Map passengerList = new HashMap<Integer, String>();

        // Passenger Data temp variables
        Integer bookingNumber = 0;
        String name = "";
        String flightCode = "";
        boolean isChecked = false;
        // parsing a CSV file into Scanner class constructor
        Scanner sc = new Scanner(new File("Datasets\\bookingList.csv"));
        sc.useDelimiter(","); // sets the delimiter pattern
        while (sc.hasNext()) // returns a boolean value
        {
            switch (scannerIndex) {
                case 0:
                    bookingNumber = Integer.parseInt(sc.next());
                    scannerIndex++;
                    break;
                case 1:
                    name = sc.next();
                    scannerIndex++;
                    break;
                case 2:
                    flightCode = sc.next();
                    scannerIndex++;
                    break;
                case 3:
                    isChecked = Boolean.valueOf(sc.next());
                    // PLACEHOLDER: The Value of passengerList will need to be an instance of Passenger class.
                    passengerList.put(bookingNumber, name);
                    scannerIndex = 0;
                    break;
            }
        }
        sc.close();// closes the scanner
        System.out.println(passengerList); 
    }
}