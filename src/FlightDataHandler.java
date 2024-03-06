package src;
//import java.io.*;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//
//public class FlightDataHandler {
//    public static void main(String[] args) throws Exception {
//        int scannerIndex = 0;
//        Map flightList = new HashMap<String, String>();
//
//        // Passenger Data temp variables
//        String flightCode = "";
//        String destination = "";
//        String company = "";
//        int maxPassengers = 0;
//        double maxBagWeight = 0.0;
//        double maxBagVolume = 0.0;
//        // parsing a CSV file into Scanner class constructor
//        Scanner sc = new Scanner(new File("Datasets\\flightList.csv"));
//        sc.useDelimiter(",|\\r?\\n"); // sets the delimiter pattern to comma or newline, handling optional carriage return
//        while (sc.hasNext()) // returns a boolean value
//        {
//            switch (scannerIndex) {
//                case 0:
//                    flightCode = sc.next();
//                    scannerIndex++;
//                    break;
//                case 1:
//                    destination = sc.next();
//                    scannerIndex++;
//                    break;
//                case 2:
//                    company = sc.next();
//                    scannerIndex++;
//                    break;
//                case 3:
//                    maxPassengers = Integer.parseInt(sc.next());
//                    scannerIndex++;
//                    break;
//                case 4:
//                    maxBagWeight = Double.parseDouble(sc.next());
//                    scannerIndex++;
//                    break;
//                case 5:
//                    maxBagVolume = Double.parseDouble(sc.next());
//                    // PLACEHOLDER: The Value of passengerList will need to be an instance of
//                    // Passenger class.
//                    flightList.put(flightCode, destination);
//                    scannerIndex = 0;
//                    break;
//            }
//        }
//        sc.close();// closes the scanner
//        System.out.println(flightList);
//    }
//}

import src.DataStructure.Flight;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FlightDataHandler {
    public static void main(String[] args) throws Exception {
        List<Flight> flightList = flightListGetter();

        // Displaying the flightList with newline after each object
        for (Flight flight : flightList) {
            System.out.println(flight);
            System.out.println(); // Adding a newline after each object
        }
    }

    public static List<Flight> flightListGetter() throws FileNotFoundException {
        List<Flight> flightList = new ArrayList<>();

        // Parsing the CSV file
        Scanner sc = new Scanner(new File("Datasets\\flightList.csv"));
        sc.nextLine(); // Skip header line
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] data = line.split(",");

            // Extracting flight data
            String flightCode = data[0];
            String airport = data[1];
            String airline = data[2];
            double excessFeePerUnitWeight = Double.parseDouble(data[3]);
            int maxCapacity = Integer.parseInt(data[4]);
            double maxLuggageWeight = Double.parseDouble(data[5]);
            double maxLuggageVolume = Double.parseDouble(data[6]);

            // Creating a Flight object and adding it to the flightList list
            Flight newFlight = new Flight(flightCode, airport, airline, excessFeePerUnitWeight, maxCapacity, maxLuggageWeight, maxLuggageVolume);
            flightList.add(newFlight);
        }
        sc.close(); // Closing the scanner

        return flightList;
    }
}


