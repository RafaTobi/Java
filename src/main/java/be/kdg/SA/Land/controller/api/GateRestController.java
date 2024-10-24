package be.kdg.SA.Land.controller.api;

import be.kdg.SA.Land.domain.Truck;
import be.kdg.SA.Land.service.AppointmentService;
import be.kdg.SA.Land.service.TruckService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/gate")
public class GateRestController {

    private final TruckService truckService;
    private final AppointmentService appointmentService;

    public GateRestController(TruckService truckService, AppointmentService appointmentService) {
        this.truckService = truckService;
        this.appointmentService = appointmentService;
    }

    @GetMapping("/entry")
    public ResponseEntity<String> checkTruckEntry(@RequestParam String licensePlate) {
        // Check if the truck exists
        Optional<Truck> truckOpt = truckService.findTruckByLicenseplate(licensePlate);

        if (truckOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Truck not found");
        }

        Truck truck = truckOpt.get();

        // Check if the truck has a valid appointment
        boolean hasValidAppointment = appointmentService.isTruckScheduledForNow(truck);

        if (!hasValidAppointment) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Truck is not scheduled for this time");
        }

        // Grant access
        return ResponseEntity.ok("Gate opened for truck: " + truck.getLicenseplate());
    }
}
