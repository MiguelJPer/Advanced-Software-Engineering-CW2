package src.main.java.com.airport_simulation.model;

import src.main.java.com.airport_simulation.data_structure.Flight;
import src.main.java.com.airport_simulation.data_structure.Passenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class CheckInDesk implements Runnable {
    private Queue<Passenger> queue; // Shared queue with passengers
    private boolean isRunning;
    private Map<String, Flight> flightsData = new HashMap<>();


    // 允许通过构造函数提供外部的flightMap，也可以不提供
    public CheckInDesk(Queue<Passenger> queue, Map<String, Flight> flightMap) {
        this.queue = queue;
        this.isRunning = true;
        // 判断外部是否提供了flightMap
        if (flightMap != null) {
            this.flightsData = flightMap;
        } else {
            loadFlightsData(); // 如果没有提供，那么加载内部的flightsData
        }
    }

    // 可能的另一个构造函数，不需要外部提供flightMap
    public CheckInDesk(Queue<Passenger> queue) {
        this(queue, null); // 调用上面的构造函数，flightMap传入null
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                processNextPassenger();
                Thread.sleep(5000); // 模拟处理一个乘客需要的时间
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrupted.");
                Thread.currentThread().interrupt();
            }
        }
    }

    // 处理队列中的下一个乘客
    public void processNextPassenger() {
        synchronized (queue) {
            while (queue.isEmpty()) {
                try {
                    queue.wait(); // 登机口线程等待，直到队列不为空
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // 重新设置中断状态
                    return; // 或其他适当的异常处理
                }
            }
            Passenger passenger = queue.poll(); // 从队列中获取下一个乘客
            String flightCode = passenger.getFlightCode(); // 获取乘客的航班号
            Flight flight = flightsData.get(flightCode); // 从航班数据Map中获取对应的Flight对象

            if (flight != null) {
                // 计算行李费用
                double baggageFee = 0;
                double excessWeight = passenger.getBaggageWeight() - flight.getFreeLuggageAllowance();
                if (excessWeight > 0) {
                    baggageFee = excessWeight * flight.getExcessLuggageCharge();
                }
                // 更新乘客的行李费用
                passenger.setBaggageFee(baggageFee);

                // 更新Flight对象的状态
                flight.setCheckedInPassengers(flight.getCheckedInPassengers() + 1); // 增加已办理登机手续的乘客数
                flight.setCarriedLuggageWeight(flight.getCarriedLuggageWeight() + passenger.getBaggageWeight()); // 增加已承载的行李重量

                // 输出日志或其他处理
                System.out.println("Passenger " + passenger.getName() + " checked in. Flight: " + flightCode + ". Baggage fee: " + baggageFee);
            } else {
                // 处理找不到对应航班的情况
                System.out.println("No flight found for flight code: " + flightCode);
            }

        }
    }


    private void loadFlightsData() {
        try (InputStream is = getClass().getResourceAsStream("/com/airport_simulation/dataset/flights.csv");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            reader.readLine(); // Skip the header line
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                // Assuming the format matches Flight.java class
                Flight flight = new Flight(data[0], Integer.parseInt(data[1]), Double.parseDouble(data[2]), data[3], data[4], Integer.parseInt(data[5]));
                flightsData.put(flight.getFlightCode(), flight);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 停止运行这个登机口
    public void stopRunning() {
        this.isRunning = false;
    }
}
