package be.kdg.SA.Land.service;

import be.kdg.SA.Land.domain.Truck;
import be.kdg.SA.Land.domain.WeighBridgeTicket;
import be.kdg.SA.Land.repository.TruckRepository;
import be.kdg.SA.Land.repository.WeighBridgedTicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class WeighBridgeTicketService {
    private final WeighBridgedTicketRepository ticketRepository;
    private final TruckRepository truckRepository;

    public WeighBridgeTicketService(WeighBridgedTicketRepository ticketRepo, TruckRepository truckRepo) {
        this.ticketRepository = ticketRepo;
        this.truckRepository = truckRepo;
    }

    public WeighBridgeTicket arrivalWeighIn(String licencePlate, long weighBridgeNumber){
        Optional<Truck> truck = truckRepository.findTruckByLicenseplate(licencePlate);
        if(truck.isEmpty()) return null;

        WeighBridgeTicket ticket = new WeighBridgeTicket();
        ticket.setWeighBridgeNumber(weighBridgeNumber);
        ticket.setTruck(truck.get());
        ticket.setArrivalTime(LocalDateTime.now());
        ticket.setArrivalWeight(truck.get().getLaadvermogen()); //TODO hoe moet dit echt gewogen worden?

        return ticket;
    }

    public WeighBridgeTicket departureWeighIn(String licencePlate){

        return null;
    }
}
