package be.kdg.SA.Land.controller.api;
import be.kdg.SA.Land.controller.Dto.AppointmentDto;
import be.kdg.SA.Land.controller.Dto.CreateAppointmentRequest;
import be.kdg.SA.Land.domain.Appointment;
import be.kdg.SA.Land.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/Appointments")
public class AppointmentRestController {
    private final AppointmentService appointmentService;
    public AppointmentRestController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/Appointment")
    ResponseEntity<AppointmentDto> findAppointmentById(@RequestParam UUID appointmentId) {
        return this.appointmentService.findByAppointmentId(appointmentId)
                .map(appointment -> ResponseEntity.ok(this.mapToDto(appointment)))
                .orElseGet(() -> ResponseEntity.notFound().build());


    }
    @PostMapping("/Appointment")
    ResponseEntity<AppointmentDto> create(@Valid @RequestBody CreateAppointmentRequest createAppointmentRequest) {
        Optional<Appointment> optionalAppointment = this.appointmentService.createAppointment(
                createAppointmentRequest.supplier(),
                createAppointmentRequest.truck(),
                createAppointmentRequest.resource(),
                createAppointmentRequest.arrivalWindow());
        return optionalAppointment
                .map(appointment -> ResponseEntity.status(HttpStatus.CREATED).body(this.mapToDto(appointment)))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }



    private AppointmentDto mapToDto(Appointment appointment) {
        return new AppointmentDto(appointment.getAppointmentId(), appointment.getSupplier(),
                appointment.getTruck(), appointment.getResource(), appointment.getArrivalWindow());
    }


}
