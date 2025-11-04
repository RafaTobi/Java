package be.kdg.sa.land.service;

import be.kdg.sa.land.domain.Truck;
import be.kdg.sa.land.domain.WeighBridgeTicket;
import be.kdg.sa.land.repository.TruckRepository;
import be.kdg.sa.land.repository.WeighBridgeTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class WeighBridgeTicketService {
    private final TruckRepository truckRepository;
    private final WeighBridgeTicketRepository ticketRepository;

    @Autowired
    public WeighBridgeTicketService(TruckRepository truckRepository, WeighBridgeTicketRepository ticketRepository) {
        this.truckRepository = truckRepository;
        this.ticketRepository = ticketRepository;
    }

    public void logTruckWeight(long weighBridgeNumber, String licensePlate, double weight, boolean isArrival) {
        Optional<Truck> truckOpt = truckRepository.findTruckByLicenseplate(licensePlate);
        if (truckOpt.isEmpty()) {
            return;
        }
        Truck truck = truckOpt.get();
        WeighBridgeTicket ticket = truck.getWbTicket();
        if (ticket == null) {
            ticket = new WeighBridgeTicket();
            ticket.setTruck(truck);
            ticket.setWeighBridgeNumber(weighBridgeNumber);
            truck.setWbTicket(ticket);
        }
        if (isArrival) {
            ticket.setArrivalWeight(weight);
            ticket.setArrivalTime(LocalDateTime.now());
        } else {
            ticket.setDepartureWeight(weight);
            ticket.setDepartureTime(LocalDateTime.now());
            ticket.setNetWeight(ticket.getDepartureWeight() - ticket.getArrivalWeight());
        }
        ticketRepository.save(ticket);
    }

    public void logTruckDeparture(Truck truck, double departureWeight) {
        WeighBridgeTicket ticket = truck.getWbTicket();
        if (ticket != null) {
            ticket.setDepartureWeight(departureWeight);
            ticket.setDepartureTime(LocalDateTime.now());
            ticket.setNetWeight(ticket.getArrivalWeight() - departureWeight);
            ticketRepository.save(ticket);
        }
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

    public long generateWeighBridgeNumber() {
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