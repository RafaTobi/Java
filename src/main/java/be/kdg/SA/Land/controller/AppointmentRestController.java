package be.kdg.SA.Land.controller;
import be.kdg.SA.Land.domain.Appointment;
import be.kdg.SA.Land.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AppointmentRestController {
    private final AppointmentService appointmentService;
    public AppointmentRestController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/Appointment")
    ResponseEntity<Appointment> findByAfspraakId(@RequestParam UUID afspraakId) {
        return this.appointmentService.findByAppointmentId(afspraakId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

}
