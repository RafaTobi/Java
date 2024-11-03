// src/main/java/be/kdg/SA/Land/service/TruckService.java
package be.kdg.SA.Land.service;

import be.kdg.SA.Land.domain.Truck;
import be.kdg.SA.Land.repository.TruckRepository;
import org.springframework.stereotype.Service;
import be.kdg.SA.Land.domain.WeighBridgeTicket;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class TruckService {
    private final TruckRepository truckRepository;
    private final FifoQueueService fifoQueueService;
    private final WeighBridgeTicketService weighBridgeTicketService;

    public TruckService(TruckRepository truckRepository, FifoQueueService fifoQueueService, WeighBridgeTicketService weighBridgeTicketService) {
        this.truckRepository = truckRepository;
        this.fifoQueueService = fifoQueueService;
        this.weighBridgeTicketService = weighBridgeTicketService;
    }

    public Truck findTruck(Truck truck) {
        Optional<Truck> existingTruck = truckRepository.findTruckByLicenseplate(truck.getLicenseplate());
        if (existingTruck.isPresent()) {
            return existingTruck.get();
        } else {
            throw new IllegalArgumentException("Truck does not exist in the database");
        }
    }

    public Optional<Truck> findTruckByLicenseplate(String licensePlate) {
        return truckRepository.findTruckByLicenseplate(licensePlate);
    }

    public Optional<Truck> checkTruckEntry(String licensePlate) {
        Optional<Truck> truckOpt = truckRepository.findTruckByLicenseplate(licensePlate);

        if (truckOpt.isEmpty()) {
            return Optional.empty();
        }

        Truck truck = truckOpt.get();
        LocalDateTime now = LocalDateTime.now();

        boolean withinAppointmentWindow = truck.getAppointments().stream()
                .anyMatch(appointment -> isWithinAppointmentWindow(
                        appointment.getArrivalWindow().getDate(),
                        appointment.getArrivalWindow().getStartTime(),
                        appointment.getArrivalWindow().getEndTime(),
                        now));

        if (withinAppointmentWindow) {
            WeighBridgeTicket ticket = weighBridgeTicketService.createTicket(truck, 1L);
            truck.setWbTicket(ticket);
            return Optional.of(truck);
        } else {
            fifoQueueService.addTruckToQueue(truck);
            return Optional.empty();
        }
    }

    private boolean isWithinAppointmentWindow(Date date, LocalTime startTime, LocalTime endTime, LocalDateTime now) {
        LocalDateTime startDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .with(startTime);

        LocalDateTime endDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .with(endTime);

        return now.isAfter(startDateTime) && now.isBefore(endDateTime);
    }
    public boolean isWithinArrivalWindow(Truck truck) {
        LocalDateTime now = LocalDateTime.now();
        return truck.getAppointments().stream()
            .anyMatch(appointment -> isWithinAppointmentWindow(
                appointment.getArrivalWindow().getDate(),
                appointment.getArrivalWindow().getStartTime(),
                appointment.getArrivalWindow().getEndTime(),
                now));
    }

    public int getTrucksInFifoQueue() {
    return fifoQueueService.getTrucksInFifoQueue();
}

public long countTrucksOnSite() {
    return truckRepository.countByOnSiteTrue();
}
}