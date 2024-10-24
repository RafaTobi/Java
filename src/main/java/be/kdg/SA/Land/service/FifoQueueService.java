// FifoQueueService.java
package be.kdg.SA.Land.service;

import be.kdg.SA.Land.domain.FifoQueue;
import be.kdg.SA.Land.domain.Truck;
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
}