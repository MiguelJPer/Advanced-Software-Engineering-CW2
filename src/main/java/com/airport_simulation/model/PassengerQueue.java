package src.main.java.com.airport_simulation.model;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import src.main.java.com.airport_simulation.data_structure.Passenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class PassengerQueue implements Runnable {
    private ObservableList<Passenger> passengers = FXCollections.observableArrayList();
    private static final Logger logger = Logger.getLogger(PassengerQueue.class.getName());
    private boolean running;
    private Random random = new Random();
    private List<String> flightCodes;
    private AtomicBoolean isFlightCodesLoaded = new AtomicBoolean(false);

    public PassengerQueue() {
        this.running = true;
        this.flightCodes = new ArrayList<>();
        logger.info("PassengerQueue 初始化");
    }

    @Override
    public void run() {
        logger.info("PassengerQueue 开始运行.");
        while (running) {
            try {
                Thread.sleep(random.nextInt(2000) + 500); // Randomly wait between 500 and 1500 milliseconds
                if (flightCodes.isEmpty()) {
                    loadFlightCodes(); // Ensure flight codes are loaded
                }
                Passenger passenger = generateRandomPassenger();
                Platform.runLater(() -> passengers.add(passenger)); // Directly modify the ObservableList on the JavaFX thread
            } catch (InterruptedException e) {
                logger.info("PassengerQueue thread interrupted.");
                running = false;
            }
        }
        logger.info("PassengerQueue 停止运行.");
    }

    private void loadFlightCodes() {
        if (isFlightCodesLoaded.compareAndSet(false, true)) {
            try (InputStream is = getClass().getResourceAsStream("/com/airport_simulation/dataset/flights.csv");
                 BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                reader.readLine(); // Skip the header row
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    flightCodes.add(data[0]); // Assuming the flight code is the first column
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Passenger generateRandomPassenger() {
        logger.info("开始生成随机乘客...");
        if (flightCodes.isEmpty()) {
            loadFlightCodes(); // 确保航班代码已加载
        }

        if (!flightCodes.isEmpty()) {
            // 现在可以安全地获取随机航班代码
            String flightCode = flightCodes.get(random.nextInt(flightCodes.size()));
            String name = "Passenger" + (random.nextInt(900) + 100); // Generates a name like "Passenger123"
            double baggageWeight = 20.0 + (20.0 * random.nextDouble()); // Generates a weight between 20.0 to 40.0
            double[] baggageDimensions = {
                    20.0 + (10.0 * random.nextDouble()), // Length between 20.0 to 30.0
                    15.0 + (5.0 * random.nextDouble()),  // Width between 15.0 to 20.0
                    10.0 + (5.0 * random.nextDouble())   // Height between 10.0 to 15.0
            };
            logger.info("随机乘客生成完成.");
            return new Passenger(flightCode, name, baggageWeight, baggageDimensions);
        } else {
            logger.warning("航班代码列表为空，无法生成随机乘客。");
            return null; // 或者返回一个默认的乘客实例，或者抛出一个异常
        }
    }


    public ObservableList<Passenger> getPassengerList() {
        return passengers;
    }

    public int getQueueSize() {
        return passengers.size();
    }

    public void stopRunning() {
        this.running = false;
    }

    public Passenger createRandomPassenger() {
        return generateRandomPassenger();
    }

    public synchronized void addPassengerDirectly(Passenger passenger) {
        this.passengers.add(passenger); // 直接添加乘客到ObservableList
    }

}
