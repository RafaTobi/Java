// TruckService.java
package be.kdg.SA.Land.service;

import be.kdg.SA.Land.domain.Supplier;
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

    public Truck findTruck(Truck truck) {
        Optional<Truck> existingTruck = truckRepository.findTruckByLicenseplate(truck.getLicenseplate());
        if (existingTruck.isPresent()) {
            return existingTruck.get();
        } else {
            throw new IllegalArgumentException("Truck does not exist in the database");
        }
    }



    public List<Truck> findAllTrucks() {
        return this.truckRepository.findAll();
    }
}