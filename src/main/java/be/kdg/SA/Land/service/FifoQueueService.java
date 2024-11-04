// FifoQueueService.java
package be.kdg.sa.land.service;

import be.kdg.sa.land.domain.FifoQueue;
import be.kdg.sa.land.domain.Truck;
import org.springframework.stereotype.Service;

@Service
public class FifoQueueService {
    private final FifoQueue fifoQueue = new FifoQueue();

    public void addTruckToQueue(Truck truck) {
        fifoQueue.addTruck(truck);
        System.out.println("Truck with license plate " + truck.getLicenseplate() + " added to FIFO queue.");
    }

    public FifoQueue getFifoQueue() {
        return fifoQueue;
    }
    public int getTrucksInFifoQueue() {
        return fifoQueue.getSize();
    }
}