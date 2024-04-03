package src.test;

import src.main.java.com.airport_simulation.model.PassengerQueue;

public class PassengerQueueTest {
    static {
        System.setProperty("java.util.logging.config.file", "./src/main/resources/com/airport_simulation/logging.properties");
    }

    public static void main(String[] args) throws InterruptedException {
        PassengerQueue passengerQueue = new PassengerQueue();
        Thread queueThread = new Thread(passengerQueue);
        queueThread.start();

        // 让乘客队列运行一段时间
        Thread.sleep(10000); // 等待5秒

        // 停止队列线程
        passengerQueue.stopRunning();
        queueThread.interrupt(); // 如果线程当前在休眠中，则中断它以退出

        // 打印队列中的乘客信息
        passengerQueue.printQueue();
    }
}
