package src.main.java.com.airport_simulation.model;

import src.main.java.com.airport_simulation.data_structure.Passenger;

import java.util.Random;
import java.util.LinkedList;
import java.util.Queue;

public class PassengerQueue implements Runnable {
    private Queue<Passenger> queue;
    private boolean running;
    private Random random = new Random();

    public PassengerQueue() {
        this.queue = new LinkedList<>();
        this.running = true;
    }

    // 新的构造函数，接受外部传入的队列
    public PassengerQueue(Queue<Passenger> queue) {
        this.queue = queue;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            try {
                // Simulate the arrival of a passenger at random intervals
                Thread.sleep(random.nextInt(3000) + 500); // Randomly wait between 500 to 1500 milliseconds

                Passenger passenger = generateRandomPassenger();
                synchronized (this) {
                    queue.add(passenger);
                    System.out.println("Passenger added to the queue: " + passenger.getName());
                }

                // Other logic to stop the thread...

            } catch (InterruptedException e) {
                System.out.println("Passenger queue thread interrupted.");
                running = false;
            }
        }
    }

    private Passenger generateRandomPassenger() {
        // Generate random passenger details
        String flightCode = "FL" + (random.nextInt(900) + 100); // Generates a flight code like "FL123"
        String name = "Passenger" + (random.nextInt(900) + 100); // Generates a name like "Passenger123"
        double baggageWeight = 15.0 + (10.0 * random.nextDouble()); // Generates a weight between 15.0 to 25.0
        double[] baggageDimensions = {
                20.0 + (10.0 * random.nextDouble()), // Length between 20.0 to 30.0
                15.0 + (5.0 * random.nextDouble()),  // Width between 15.0 to 20.0
                10.0 + (5.0 * random.nextDouble())   // Height between 10.0 to 15.0
        };

        return new Passenger(flightCode, name, baggageWeight, baggageDimensions);
    }

    // Getters and setters...

    public synchronized Queue<Passenger> getQueue() {
        return queue;
    }

    public void stopRunning() {
        this.running = false;
    }

    public synchronized void printQueue() {
        System.out.println("Current Queue: ");
        for (Passenger passenger : queue) {
            System.out.println("Passenger Name: " + passenger.getName() + ", Flight Code: " + passenger.getFlightCode()
                    + ", Baggage Weight: " + passenger.getBaggageWeight() + ", Baggage Dimensions: "
                    + "[L: " + passenger.getBaggageDimensions()[0] + ", W: " + passenger.getBaggageDimensions()[1]
                    + ", H: " + passenger.getBaggageDimensions()[2] + "]");
        }
    }

}
