package src.test;

import src.main.java.com.airport_simulation.model.CheckInDesk;
import src.main.java.com.airport_simulation.model.PassengerQueue;
import src.main.java.com.airport_simulation.data_structure.Flight;
import src.main.java.com.airport_simulation.data_structure.Passenger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CheckInDeskTest {
    public static void main(String[] args) throws InterruptedException {
        // 创建一个包含若干航班的映射
        Map<String, Flight> flights = new HashMap<>();
        flights.put("FL100", new Flight("FL100"));
        flights.put("FL200", new Flight("FL200"));
        flights.put("FL300", new Flight("FL300"));

        // 创建一个共享的乘客队列
        ConcurrentLinkedQueue<Passenger> queue = new ConcurrentLinkedQueue<>();

        // 启动一个线程模拟乘客随机到达并加入队列
        PassengerQueue passengerQueue = new PassengerQueue(queue);
        new Thread(passengerQueue, "PassengerQueueThread").start();

        // 启动CheckInDesk线程处理队列中的乘客
        CheckInDesk checkInDesk1 = new CheckInDesk(queue, flights);
        Thread deskThread1 = new Thread(checkInDesk1, "CheckInDesk1");
        deskThread1.start();

        // 模拟运行一段时间后停止
        Thread.sleep(10000); // 运行10秒

        // 停止乘客队列和登机口线程
        passengerQueue.stopRunning();
        checkInDesk1.stopRunning();
        deskThread1.interrupt();

        // 打印航班登机信息
        flights.forEach((code, flight) -> {
            System.out.println("Flight " + code + ": " + flight.getPassengers().size() + " passengers, Total baggage weight: " + flight.getTotalBaggageWeight());
        });
    }
}
