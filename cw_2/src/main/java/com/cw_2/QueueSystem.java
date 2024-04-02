import java.util.LinkedList;
import java.util.Queue;

public class QueueSystem {
    private final Queue<Passenger> passengerQueue;

    public QueueSystem() {
        passengerQueue = new LinkedList<>();
    }
    public synchronized void addPassengerToQueue(Passenger passenger) {
        passengerQueue.add(passenger);
        System.out.println("Passenger added to the queue: " + passenger.getId());
    }
  
    public synchronized Passenger processNextPassenger() {
        if (!passengerQueue.isEmpty()) {
            return passengerQueue.poll();
        }
        return null; 
    }

    public synchronized int getQueueSize() {
        return passengerQueue.size();
    }
}
