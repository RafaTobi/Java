package be.kdg.SA.Land.service;

import be.kdg.SA.Land.domain.ArrivalWindow;
import be.kdg.SA.Land.domain.Appointment;
import be.kdg.SA.Land.domain.Truck;
import be.kdg.SA.Land.repository.AppointmentRepository;
import be.kdg.SA.Land.repository.TruckRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class GateService {
    private final AppointmentRepository appointmentRepository;
    private final TruckRepository truckRepository;

    public GateService(AppointmentRepository appointmentRepository, TruckRepository truckRepository) {
        this.appointmentRepository = appointmentRepository;
        this.truckRepository = truckRepository;
    }

    public void openPoort(String nummerplaat){
        Optional<Truck> vrachtwagen = truckRepository.findVrachtwagenByNummerplaat(nummerplaat);
        if(vrachtwagen.isEmpty()) {
            System.out.println("Truck niet herkend.");
            return;
        }

        Optional<List<Appointment>> afspraken = appointmentRepository.findAfspraakByVrachtwagen(vrachtwagen.get());
        if (afspraken.isEmpty()) {
            System.out.println("Er zijn geen afspraken gemaakt voor vrachtwagen met nummerplaat: " + vrachtwagen.get().getNummerplaat());
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        boolean onTime = false;
        for (Appointment appointment : afspraken.get()) {
            ArrivalWindow arrivalWindow = appointment.getAankomstVenster();
            if (now.isAfter(arrivalWindow.getAankomstTijd()) && now.isBefore(arrivalWindow.getVertrekTijd())) {
                onTime = true;
                System.out.println("Truck met nummerplaat: " + vrachtwagen.get().getNummerplaat() + " is op tijd. Poort gaat nu open...");
                break;
            } else {
                System.out.println("Truck is niet aanwezig tijdens een aankomstvenster.");
            }
        }
    }
}
