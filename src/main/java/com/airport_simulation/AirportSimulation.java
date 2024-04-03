package src.main.java.com.airport_simulation;

import src.main.java.com.airport_simulation.data_structure.Flight;
import src.main.java.com.airport_simulation.data_structure.Passenger;
import src.main.java.com.airport_simulation.model.CheckInDesk;
import src.main.java.com.airport_simulation.model.PassengerQueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class AirportSimulation {

    public static void main(String[] args) {
        // 创建 PassengerQueue 实例
        PassengerQueue passengerQueue = new PassengerQueue();

        // 创建并启动 PassengerQueue 线程
        Thread passengerQueueThread = new Thread(passengerQueue);
        passengerQueueThread.start();

        // 定义 CheckInDesk 数量
        final int numberOfDesks = 2;

        // 创建并启动 CheckInDesk 线程
        for (int i = 0; i < numberOfDesks; i++) {
            CheckInDesk checkInDesk = new CheckInDesk(passengerQueue.getQueue());
            Thread deskThread = new Thread(checkInDesk, "CheckInDesk-" + i);
            deskThread.start();

            // 添加一些逻辑以优雅地关闭线程，例如使用shutdown钩子或其他机制
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
    }
}



