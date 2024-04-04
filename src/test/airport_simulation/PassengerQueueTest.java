package src.test.airport_simulation;
import src.main.java.com.airport_simulation.data_structure.Passenger;
import src.main.java.com.airport_simulation.model.PassengerQueue;

public class PassengerQueueTest {

    public static void main(String[] args) {
        PassengerQueue passengerQueue = new PassengerQueue();

        // 模拟添加乘客到队列
        for (int i = 0; i < 5; i++) { // 生成并打印5个乘客信息作为测试
            Passenger passenger = passengerQueue.createRandomPassenger();
            passengerQueue.addPassengerDirectly(passenger); // 假设addPassenger方法添加乘客到队列

            // 打印生成的乘客信息
            System.out.println("Generated Passenger:");
            System.out.println("Flight Code: " + passenger.getFlightCode());
            System.out.println("Name: " + passenger.getName());
            System.out.println("Baggage Weight: " + passenger.getBaggageWeight());
            System.out.println("Baggage Dimensions: " + String.join(" x ", passenger.getBaggageDimensions()));
            System.out.println("-------------------------------------");
        }

        // 可选：打印当前队列中所有乘客的信息
        System.out.println("Current queue:");
        for (Passenger passenger : passengerQueue.getPassengerList()) {
            System.out.println(passenger);
        }
    }
}
