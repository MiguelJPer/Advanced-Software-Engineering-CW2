package src.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlightDataGenerator {

    public static void main(String[] args) {
        generateFlightDataFromBookingList();
    }

    public static void generateFlightDataFromBookingList() {
        List<String> flightCodes = readFlightCodesFromBookingList("Datasets/bookingList.csv");
        if (flightCodes == null) {
            System.err.println("Failed to read flight codes from booking list.");
            return;
        }

        generateFlightData(flightCodes);
    }

    public static List<String> readFlightCodesFromBookingList(String filePath) {
        List<String> flightCodes = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) { // Ensure there are at least three columns
                    flightCodes.add(parts[2]); // Add flightCode from the third column
                }
            }
            br.close();
            return flightCodes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void generateFlightData(List<String> flightCodes) {
        Random random = new Random();
        try {
            FileWriter fw = new FileWriter("Datasets/flightList.csv");
            BufferedWriter bw = new BufferedWriter(fw);

            for (String flightCode : flightCodes) {
                String airport = generateRandomAirport();
                String airline = generateRandomAirline();
                double excessFeePerUnitWeight = random.nextDouble() * 100;
                int maxCapacity = random.nextInt(300) + 100; // Random capacity between 100 and 400
                double maxLuggageWeight = random.nextDouble() * 200;
                double maxLuggageVolume = random.nextDouble() * 500;

                bw.write(flightCode + "," + airport + "," + airline + "," + excessFeePerUnitWeight + "," +
                        maxCapacity + "," + maxLuggageWeight + "," + maxLuggageVolume);
                bw.newLine();
            }

            bw.close();
            System.out.println("Flight data generated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateRandomAirport() {
        String[] airports = {"JFK", "LAX", "SFO", "ORD", "ATL", "LHR", "CDG", "DXB", "HND", "PEK"};
        Random random = new Random();
        return airports[random.nextInt(airports.length)];
    }

    public static String generateRandomAirline() {
        String[] airlines = {"Delta", "American Airlines", "United Airlines", "Lufthansa", "Air France", "Emirates", "British Airways", "China Southern", "Qatar Airways", "Singapore Airlines"};
        Random random = new Random();
        return airlines[random.nextInt(airlines.length)];
    }
}
