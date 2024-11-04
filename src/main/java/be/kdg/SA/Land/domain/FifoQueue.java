
package be.kdg.sa.land.domain;

import java.util.LinkedList;
import java.util.Queue;

public class FifoQueue {
    private Queue<Truck> queue = new LinkedList<>();

    public void addTruck(Truck truck) {
        queue.add(truck);
    }

    public Truck removeTruck() {
        return queue.poll();
    }

    public Queue<Truck> getQueue() {
        return queue;
    }

    public int getSize() {
        return queue.size();
    }
}