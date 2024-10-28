package be.kdg.SA.Land.service;

import be.kdg.SA.Land.domain.Truck;
import be.kdg.SA.Land.domain.WeighBridgeTicket;
import be.kdg.SA.Land.repository.TruckRepository;
import be.kdg.SA.Land.repository.WeighBridgedTicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class WeighBridgeTicketService {
    private final WeighBridgedTicketRepository ticketRepository;
    private final TruckRepository truckRepository;

    public WeighBridgeTicketService(WeighBridgedTicketRepository ticketRepo, TruckRepository truckRepo) {
        this.ticketRepository = ticketRepo;
        this.truckRepository = truckRepo;
    }

    public WeighBridgeTicket arrivalWeighIn(String licencePlate, long weighBridgeNumber){
        Optional<Truck> truckOpt = truckRepository.findTruckByLicenseplate(licencePlate);
        if (truckOpt.isEmpty()) return null;

        Truck truck = truckOpt.get();
        WeighBridgeTicket ticket = new WeighBridgeTicket();
        ticket.setWeighBridgeNumber(weighBridgeNumber);
        ticket.setTruck(truck);
        ticket.setArrivalTime(LocalDateTime.now());
        ticket.setArrivalWeight(truck.getLaadvermogen());
        ticketRepository.save(ticket);
        truck.setWbTicket(ticket);
        truckRepository.save(truck);

        return ticket;
    }

    public WeighBridgeTicket departureWeighIn(String licencePlate) {

        Optional<Truck> truckOpt = truckRepository.findTruckByLicenseplate(licencePlate);
        if (truckOpt.isEmpty()) return null;

        Truck truck = truckOpt.get();
        Optional<WeighBridgeTicket> ticketOpt = ticketRepository.findByTruckAndDepartureTimeIsNull(truck);
        if (ticketOpt.isEmpty()) return null;

        WeighBridgeTicket ticket = ticketOpt.get();
        ticket.setDepartureTime(LocalDateTime.now());
        ticket.setDepartureWeight(truck.getLaadvermogen());

        double netWeight = ticket.getArrivalWeight() - ticket.getDepartureWeight();
        ticket.setNetWeight(netWeight);

        ticketRepository.save(ticket);
        return ticket;
    }

    public long assignWeighBridgeNumber(String licencePlate) {
        Optional<Truck> truckOpt = truckRepository.findTruckByLicenseplate(licencePlate);
        if (truckOpt.isEmpty()) return -1;

        Truck truck = truckOpt.get();

        WeighBridgeTicket ticket = new WeighBridgeTicket();
        long weighBridgeNumber = generateWeighBridgeNumber();
        ticket.setWeighBridgeNumber(weighBridgeNumber);
        ticket.setTruck(truck);
        ticket.setArrivalTime(LocalDateTime.now());

        ticket.setArrivalWeight(truck.getLaadvermogen());
        ticketRepository.save(ticket);
        truck.setWbTicket(ticket);
        truckRepository.save(truck);

        return weighBridgeNumber;
    }

    private long generateWeighBridgeNumber() {
          return new Random().nextLong();
    }
}