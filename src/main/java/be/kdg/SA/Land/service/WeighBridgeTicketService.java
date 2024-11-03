// src/main/java/be/kdg/SA/Land/service/WeighBridgeTicketService.java
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

    public WeighBridgeTicketService(WeighBridgedTicketRepository ticketRepository, TruckRepository truckRepository) {
        this.ticketRepository = ticketRepository;
        this.truckRepository = truckRepository;
    }

    public WeighBridgeTicket arrivalWeighIn(String licensePlate, long weighBridgeNumber) {
        Optional<Truck> truckOpt = truckRepository.findTruckByLicenseplate(licensePlate);
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

    public WeighBridgeTicket departureWeighIn(String licensePlate) {
        Optional<Truck> truckOpt = truckRepository.findTruckByLicenseplate(licensePlate);
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

    public long assignWeighBridgeNumber(String licensePlate) {
        Optional<Truck> truckOpt = truckRepository.findTruckByLicenseplate(licensePlate);
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
        return new Random().nextInt(100) + 1;
    }

    public WeighBridgeTicket createTicket(Truck truck, Long weighBridgeNumber) {
        WeighBridgeTicket ticket = new WeighBridgeTicket();
        ticket.setTruck(truck);
        ticket.setWeighBridgeNumber(weighBridgeNumber);
        ticket.setArrivalTime(LocalDateTime.now());
        ticketRepository.save(ticket);
        return ticket;
    }



    public WeighBridgeTicket updateTicketForDeparture(Truck truck, Long newWeighBridgeNumber) {
        WeighBridgeTicket ticket = truck.getWbTicket();
        ticket.setWeighBridgeNumber(newWeighBridgeNumber);
        return ticket;
    }
}