package src.main.java.com.airport_simulation.model;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import src.main.java.com.airport_simulation.data_structure.Flight;
import src.main.java.com.airport_simulation.data_structure.Passenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class AirportSimulation {
    static {
        System.setProperty("java.util.logging.config.file", "./src/main/resources/com/airport_simulation/logging.properties");
    }

    public static void main(String[] args) {
        // 需要在JavaFX应用线程中运行UI相关代码
        Platform.startup(() -> {
            // 创建 PassengerQueue 实例
            ObservableList<Passenger> passengerList = FXCollections.observableArrayList();
            PassengerQueue passengerQueue = new PassengerQueue(passengerList); // 修改此处以传递ObservableList

            // 创建并启动 PassengerQueue 线程
            Thread passengerQueueThread = new Thread(passengerQueue);
            passengerQueueThread.start();

            // 定义 CheckInDesk 数量
            final int numberOfDesks = 2;

            // 创建并启动 CheckInDesk 线程
            for (int i = 0; i < numberOfDesks; i++) {
                CheckInDesk checkInDesk = new CheckInDesk(passengerList); // 修改此处以传递ObservableList
                Thread deskThread = new Thread(checkInDesk, "CheckInDesk-" + i);
                deskThread.start();
            }

            // 添加一个简单的shutdown钩子来优雅地停止所有线程
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Shutting down simulation...");
                passengerQueue.stopRunning(); // 停止 PassengerQueue 线程
                // 注意：对于CheckInDesk，可能需要实现一个停止方法来安全地结束线程
            }));

            // 等待一定时间后，模拟结束模拟
            try {
                Thread.sleep(15000); // 假设模拟运行15秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(0); // 触发shutdown钩子，停止所有线程
        });
    }
}
