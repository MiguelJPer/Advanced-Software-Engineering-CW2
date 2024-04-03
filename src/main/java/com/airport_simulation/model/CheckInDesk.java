package src.main.java.com.airport_simulation.model;

import src.main.java.com.airport_simulation.data_structure.Flight;
import src.main.java.com.airport_simulation.data_structure.Passenger;

import java.util.Map;
import java.util.Queue;

public class CheckInDesk implements Runnable {
    private Queue<Passenger> queue; // Shared queue with passengers
    private Map<String, Flight> flightMap; // Map of flight codes to flights
    private boolean isRunning;

    // 构造函数，初始化登机口
    public CheckInDesk(Queue<Passenger> queue, Map<String, Flight> flightMap) {
        this.queue = queue;
        this.flightMap = flightMap;
        this.isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                processNextPassenger();
                // 模拟处理时间，让其他线程有机会执行
                Thread.sleep(500); // 假设处理一个乘客需要0.5秒
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrupted.");
                Thread.currentThread().interrupt(); // 重新设置中断状态
            }
        }
    }

    // 处理队列中的下一个乘客
    private void processNextPassenger() {
        synchronized (queue) { // 确保线程安全地访问队列
            if (!queue.isEmpty()) {
                Passenger passenger = queue.poll(); // 从队列中取出一个乘客
                System.out.println("Processing passenger: " + passenger.getName());
                // 这里可以添加更多的逻辑，比如根据乘客的航班号分配到相应的航班
                Flight flight = flightMap.get(passenger.getFlightCode());
                if (flight != null) {
                    // 假设有一个方法来处理乘客登机手续
                    flight.addPassenger(passenger);
                }
            }
        }
    }

    // 停止运行这个登机口
    public void stopRunning() {
        this.isRunning = false;
    }
}
