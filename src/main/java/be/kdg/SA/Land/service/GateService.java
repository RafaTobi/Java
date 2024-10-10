package be.kdg.SA.Land.service;

import be.kdg.SA.Land.domain.ArrivalWindow;
import be.kdg.SA.Land.domain.Appointment;
import be.kdg.SA.Land.domain.Truck;
import be.kdg.SA.Land.repository.AppointmentRepository;
import be.kdg.SA.Land.repository.TruckRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GateService {
    private final AppointmentRepository appointmentRepository;
    private final TruckRepository truckRepository;
    private final Logger logger = Logger.getLogger(GateService.class.getName());

    public GateService(AppointmentRepository appointmentRepository, TruckRepository truckRepository) {
        this.appointmentRepository = appointmentRepository;
        this.truckRepository = truckRepository;
    }

    public void openPoort(String licenseplate){
        Optional<Truck> vrachtwagen = truckRepository.findTruckByLicenseplate(licenseplate);
        if(vrachtwagen.isEmpty()) {
            logger.log(Level.INFO, "Truck niet herkend.");
            return;
        }

        Optional<List<Appointment>> afspraken = appointmentRepository.findAppointmentByTruck(vrachtwagen.get());
        if (afspraken.isEmpty()) {
            logger.log(Level.INFO, "Er zijn geen afspraken gemaakt voor vrachtwagen met licenseplate: " + vrachtwagen.get().getLicenseplate());
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        boolean onTime = false;
        for (Appointment appointment : afspraken.get()) {
            ArrivalWindow arrivalWindow = appointment.getArrivalWindow();
            if (now.isAfter(arrivalWindow.getAankomstTijd()) && now.isBefore(arrivalWindow.getVertrekTijd())) {
                onTime = true;
                logger.log(Level.INFO, "Truck met license: " + vrachtwagen.get().getLicenseplate() + " is op tijd. Poort gaat nu open...");
                break;
            } else {
                logger.log(Level.INFO, "Truck is niet aanwezig tijdens een aankomstvenster.");
            }
        }
    }
}
