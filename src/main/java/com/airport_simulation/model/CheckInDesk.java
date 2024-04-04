package src.main.java.com.airport_simulation.model;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import src.main.java.com.airport_simulation.data_structure.Flight;
import src.main.java.com.airport_simulation.data_structure.Passenger;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;

public class CheckInDesk implements Runnable {
    private ObservableList<Passenger> queue;
    private boolean isRunning;
    private double baggageFee = 0;
    private Map<String, Flight> flightsData = new HashMap<>();

    // 新增一个用于UI展示航班信息的 ObservableList
    private ObservableList<String> flightInfoForDisplay = FXCollections.observableArrayList();



    private Consumer<String> onPassengerProcessed;
    public void setOnPassengerProcessed(Consumer<String> listener) {
        this.onPassengerProcessed = listener;
    }

    public CheckInDesk(ObservableList<Passenger>queue, Map<String, Flight> flightMap) {
        this.queue = queue;
        this.isRunning = true;
        // 判断外部是否提供了flightMap
        if (flightMap != null) {
            this.flightsData = flightMap;
        } else {
            loadFlightsData(); // 如果没有提供，那么加载内部的flightsData
        }
        updateFlightInfoForDisplay();
    }

    // 可能的另一个构造函数，不需要外部提供flightMap
    public CheckInDesk(ObservableList<Passenger> queue) {
        this(queue, null); // 调用上面的构造函数，flightMap传入null
    }

    @Override
    public void run() {
        while (isRunning) {
            processNextPassenger();
            try {
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
            Passenger passenger = queue.remove(0); // 从队列中获取下一个乘客
            String flightCode = passenger.getFlightCode(); // 获取乘客的航班号
            Flight flight = flightsData.get(flightCode); // 从航班数据Map中获取对应的Flight对象

            if (flight != null) {
                // 处理乘客数据
                double excessWeight = passenger.getBaggageWeight() - flight.getFreeLuggageAllowance();
                if (excessWeight > 0) {
                    baggageFee = excessWeight * flight.getExcessLuggageCharge();
                }
                if (passenger != null) {
                    String message = passenger.getName() + " is dropping of one bag of " + passenger.getBaggageWeight() + ". A baggage fee of " + baggageFee + "is due.";
                    // 使用Platform.runLater来确保在JavaFX主线程上调用
                    Platform.runLater(() -> {
                        if (onPassengerProcessed != null) {
                            onPassengerProcessed.accept(message);
                        }
                    });
                }

                // 处理航班数据
                updateFlightsData(flight, passenger);
                updateFlightInfoForDisplay(); // 更新UI展示列表


            } else {
                // 处理找不到对应航班的情况
                System.out.println("No flight found for flight code: " + flightCode);
            }
        }

    }

    private void updateFlightsData(Flight flight, Passenger passenger){
        // 更新Flight对象的状态
        flight.setCheckedInPassengers(flight.getCheckedInPassengers() + 1); // 增加已办理登机手续的乘客数
        flight.setCarriedLuggageWeight(flight.getCarriedLuggageWeight() + passenger.getBaggageWeight()); // 增加已承载的行李重量
        // 写入数据
        updateFlightsCsv();
    }

    // 在处理完每个乘客或航班数据更新后调用此方法来更新UI展示列表
    private void updateFlightInfoForDisplay() {
        Platform.runLater(() -> {
            flightInfoForDisplay.clear(); // 先清空当前列表
            for (Map.Entry<String, Flight> entry : flightsData.entrySet()) {
                String flightCode = entry.getKey();
                Flight flight = entry.getValue();
                String displayText = flightCode + ": 已登机乘客数 " + flight.getCheckedInPassengers()
                        + ", 行李总重 " + flight.getCarriedLuggageWeight();
                flightInfoForDisplay.add(displayText); // 添加到UI展示列表
            }
        });
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

    // 假设这个方法在处理完一个乘客后被调用
    private void updateFlightsCsv() {
        String csvPath = "src/main/resources/com/airport_simulation/dataset/flights.csv"; // CSV文件的路径
        try (PrintWriter writer = new PrintWriter(new File(csvPath))) {
            // 写入文件头
            writer.println("AirlineName,FreeLuggageAllowance,ExcessLuggageCharge,FlightCode,Destination,LuggageCapacity,CheckedInPassengers,CarriedLuggageWeight");
            // 遍历flightsData Map，并将每个Flight对象的信息写入文件
            for (Flight flight : flightsData.values()) {
                String line = String.join(",",
                        flight.getAirlineName(),
                        String.valueOf(flight.getFreeLuggageAllowance()),
                        String.valueOf(flight.getExcessLuggageCharge()),
                        flight.getFlightCode(),
                        flight.getDestination(),
                        String.valueOf(flight.getLuggageCapacity()),
                        String.valueOf(flight.getCheckedInPassengers()),
                        String.valueOf(flight.getCarriedLuggageWeight()));
                writer.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 停止运行这个登机口
    public void stopRunning() {
        this.isRunning = false;
    }

    // 提供一个公共方法以便外部访问更新后的航班信息展示列表
    public ObservableList<String> getFlightInfoForDisplay() {
        return flightInfoForDisplay;
    }

}
