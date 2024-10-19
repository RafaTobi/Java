// TruckService.java
package be.kdg.SA.Land.service;

import be.kdg.SA.Land.domain.Appointment;
import be.kdg.SA.Land.domain.Truck;
import be.kdg.SA.Land.repository.TruckRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TruckService {
    private final TruckRepository truckRepository;

    public TruckService(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    public Truck findOrCreateTruck(Truck truck) {
        Optional<Truck> existingTruck = truckRepository.findTruckByLicenseplate(truck.getLicenseplate());
        return existingTruck.orElseGet(() -> truckRepository.save(truck));
    }

    public List<Truck> findAllTrucks() {
        return this.truckRepository.findAll();
    }
}