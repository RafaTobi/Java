package be.kdg.SA.Land.service;

import be.kdg.SA.Land.controller.dto.WeighingOperationDto;
import be.kdg.SA.Land.domain.Truck;
import be.kdg.SA.Land.domain.WeighBridgeTicket;
import be.kdg.SA.Land.repository.TruckRepository;
import be.kdg.SA.Land.repository.WeighBridgeTicketRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeighingOperationService {
    private final TruckRepository truckRepository;
    private final WeighBridgeTicketRepository weighBridgeTicketRepository;

    public WeighingOperationService(TruckRepository truckRepository, WeighBridgeTicketRepository weighBridgeTicketRepository) {
        this.truckRepository = truckRepository;
        this.weighBridgeTicketRepository = weighBridgeTicketRepository;
    }

    public void processWeighingOperation(WeighingOperationDto weighingOperationDto) {
        Optional<Truck> truckOpt = truckRepository.findTruckByLicenseplate(weighingOperationDto.getLicensePlate());

        if (truckOpt.isPresent()) {
            Truck truck = truckOpt.get();
            WeighBridgeTicket ticket = new WeighBridgeTicket();
            ticket.setTruck(truck);
            ticket.setArrivalTime(weighingOperationDto.getTimestamp());
            ticket.setArrivalWeight(weighingOperationDto.getWeight());
            weighBridgeTicketRepository.save(ticket);
            truck.setWbTicket(ticket);
            truckRepository.save(truck);
        } else {
            throw new IllegalArgumentException("Truck with license plate " + weighingOperationDto.getLicensePlate() + " not found.");
        }
    }
}
