package src.main.java.com.airport_simulation.model;
import src.main.java.com.airport_simulation.data_structure.Passenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class PassengerQueue implements Runnable {
    private static final Logger logger = Logger.getLogger(PassengerQueue.class.getName());
    private Queue<Passenger> queue;
    private boolean running;
    private Random random = new Random();
    private List<String> flightCodes;
    private AtomicBoolean isFlightCodesLoaded = new AtomicBoolean(false);

    public PassengerQueue() {
        this.queue = new LinkedList<>();
        this.running = true;
        this.flightCodes = new ArrayList<>();
        logger.info("PassengerQueue 初始化");
    }

    // 新的构造函数，接受外部传入的队列
    public PassengerQueue(Queue<Passenger> queue) {
        this.queue = queue;
        this.running = true;
        logger.info("PassengerQueue 初始化");
    }

    @Override
    public void run() {
        logger.info("PassengerQueue 开始运行.");
        while (running) {
            try {
                // Simulate the arrival of a passenger at random intervals
                Thread.sleep(random.nextInt(2000) + 500); // Randomly wait between 500 and 1500 milliseconds

                Passenger passenger = generateRandomPassenger();
                synchronized (queue) {
                    queue.add(passenger);
                    queue.notifyAll(); // 唤醒所有等待的线程
                    System.out.println("Passenger Name: " + passenger.getName() + ", Flight Code: " + passenger.getFlightCode()
                            + ", Baggage Weight: " + passenger.getBaggageWeight() + ", Baggage Dimensions: "
                            + "[L: " + passenger.getBaggageDimensions()[0] + ", W: " + passenger.getBaggageDimensions()[1]
                            + ", H: " + passenger.getBaggageDimensions()[2] + "]");
                }
                // Other logic to stop the thread...
            } catch (InterruptedException e) {
                System.out.println("Passenger queue thread interrupted.");
                running = false;
            }
        }
        logger.info("PassengerQueue 停止运行.");
    }

    private void loadFlightCodes() {
        logger.info("开始加载航班代码...");
        if (isFlightCodesLoaded.compareAndSet(false, true)) { // Only enter this block if isFlightCodesLoaded was false
            try {
                InputStream is = getClass().getResourceAsStream("/com/airport_simulation/dataset/flights.csv");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                reader.readLine(); // 跳过第一行（标题行）
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(","); // Assuming CSV format is correct and flight code is the first element
                    flightCodes.add(data[3]); // Add the flight code to the list
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("航班代码加载完成");

    }

    private Passenger generateRandomPassenger() {
        logger.info("开始生成随机乘客...");
        if (flightCodes.isEmpty()) {
            loadFlightCodes(); // Ensure flight codes are loaded

        }
        // Generate random passenger details
        String flightCode = flightCodes.get(random.nextInt(flightCodes.size()));
        String name = "Passenger" + (random.nextInt(900) + 100); // Generates a name like "Passenger123"
        double baggageWeight = 30.0 + (10.0 * random.nextDouble()); // Generates a weight between 25.0 to 35.0
        double[] baggageDimensions = {
                20.0 + (10.0 * random.nextDouble()), // Length between 20.0 to 30.0
                15.0 + (5.0 * random.nextDouble()),  // Width between 15.0 to 20.0
                10.0 + (5.0 * random.nextDouble())   // Height between 10.0 to 15.0
        };
        double baggageFee = 0;
        logger.info("随机乘客生成完成.");
        return new Passenger(flightCode, name, baggageWeight, baggageDimensions, baggageFee);

    }

    public Passenger createRandomPassenger() {
        return generateRandomPassenger();
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
