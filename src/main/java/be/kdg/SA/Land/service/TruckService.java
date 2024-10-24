// TruckService.java
package be.kdg.SA.Land.service;

import be.kdg.SA.Land.domain.Truck;
import be.kdg.SA.Land.repository.TruckRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
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

    public Optional<Truck> findTruckByLicenseplate(String licensePlate) {
        return truckRepository.findTruckByLicenseplate(licensePlate);
    }

    public String checkTruckEntry(String licensePlate) {
        Optional<Truck> truckOpt = truckRepository.findTruckByLicenseplate(licensePlate);

        if (truckOpt.isPresent()) {
            Truck truck = truckOpt.get();
            LocalDateTime now = LocalDateTime.now();
            boolean hasValidAppointment = truck.getAppointments().stream()
                    .anyMatch(appointment -> {
                        LocalDateTime startDateTime = appointment.getArrivalWindow().getDate().toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime()
                                .with(appointment.getArrivalWindow().getStartTime());

                        LocalDateTime endDateTime = appointment.getArrivalWindow().getDate().toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime()
                                .with(appointment.getArrivalWindow().getEndTime());

                        return now.isAfter(startDateTime) && now.isBefore(endDateTime);
                    });

            if (hasValidAppointment) {
                return "Gate opened for truck with license plate " + licensePlate + ". Proceed to weighbridge number: " + truck.getWbTicket().getWeighBridgeNumber();
            } else {
                return "Truck with license plate " + licensePlate + " is not allowed to enter. No valid appointment found.";
            }
        } else {
            return "No truck found with license plate " + licensePlate;
        }
    }
}