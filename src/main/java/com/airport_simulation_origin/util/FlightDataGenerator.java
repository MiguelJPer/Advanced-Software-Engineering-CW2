//package src.main.java.com.airport_simulation_origin.util;
//
//import src.main.java.com.airport_simulation_origin.data_structure.Flight;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class FlightDataGenerator {
//    private static final String[] destinations = {"Beijing", "New York", "London", "Dubai", "Tokyo"};
//
//    public static void main(String[] args) {
//        List<Airline> airlines = new ArrayList<>();
//        airlines.add(new Airline("American Airlines", 30, 100.0));
//        airlines.add(new Airline("United Airlines", 25, 150.0));
//        airlines.add(new Airline("British Airways", 20, 200.0));
//        airlines.add(new Airline("Emirates", 35, 250.0));
//        airlines.add(new Airline("Qatar Airways", 40, 300.0));
//
//        List<Flight> flights = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            Airline airline = airlines.get(new Random().nextInt(airlines.size()));
//            String flightNumber = generateFlightNumber();
//            String destination = destinations[new Random().nextInt(destinations.length)];
//            int luggageCapacity = 1000 + new Random().nextInt(1000); // 假定行李容量为1000至2000之间
//
//            flights.add(new Flight(airline.getName(), airline.getFreeLuggageAllowance(), airline.getExcessLuggageCharge(), flightNumber, destination, luggageCapacity));
//        }
//
//        writeFlightsToCsv(flights);
//        System.out.println("Flight data has been generated and saved to flights.csv");
//    }
//
//    private static String generateFlightNumber() {
//        Random random = new Random();
//        return (char) ('A' + random.nextInt(26)) + "" + (char) ('A' + random.nextInt(26)) + String.format("%03d", random.nextInt(1000));
//    }
//
//    private static void writeFlightsToCsv(List<Flight> flights) {
//        try (FileWriter writer = new FileWriter("src/main/resources/com/airport_simulation/dataset/flights.csv")) {
//            writer.append("Airline Name,Free Luggage Allowance,Excess Luggage Charge,Flight Number,Destination,Luggage Capacity,Checked In Passengers,Carried Luggage Weight\n");
//            for (Flight flight : flights) {
//                writer.append(String.format("%s,%d,%.2f,%s,%s,%d,%d,%.2f\n", flight.getAirlineName(), flight.getFreeLuggageAllowance(), flight.getExcessLuggageCharge(), flight.getFlightCode(), flight.getDestination(), flight.getLuggageCapacity(), flight.getCheckedInPassengers(), flight.getCarriedLuggageWeight()));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
