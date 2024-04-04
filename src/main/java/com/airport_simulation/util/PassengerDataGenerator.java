package src.main.java.com.airport_simulation.util;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class PassengerDataGenerator {

    public static void main(String[] args) {
        generatePassengerData(100); // Generate data for 100 passengers
    }

    public static void generatePassengerData(int numPassengers) {
        Random random = new Random();
        try {
            FileWriter fw = new FileWriter("src/main/resources/com/airport_simulation/dataset/passengerList.csv");
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < numPassengers; i++) {
                String bookingReferenceCode = String.format("%05d", random.nextInt(100000));
                String name = generateRandomName();
                String flightCode = generateRandomFlightCode();
                boolean checkedIn = false;

                bw.write(bookingReferenceCode + "," + name + "," + flightCode + "," + checkedIn);
                bw.newLine();
            }

            bw.close();
            System.out.println("Passenger data generated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateRandomName() {
        String[] firstName = {"John", "Emily", "Michael", "Sophia", "William", "Emma", "Matthew", "Olivia", "Daniel", "Ava"};
        String[] lastName = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez"};

        Random random = new Random();
        String randomFirstName = firstName[random.nextInt(firstName.length)];
        String randomLastName = lastName[random.nextInt(lastName.length)];

        return randomFirstName + " " + randomLastName;
    }

    public static String generateRandomFlightCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        char[] letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        // First two characters are uppercase letters
        for (int i = 0; i < 2; i++) {
            char letter = letters[random.nextInt(letters.length)];
            sb.append(letter);
        }

        // Last two characters are digits
        for (int i = 0; i < 2; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }

        return sb.toString();
    }
}
