// AppointmentService.java
package be.kdg.SA.Land.service;

import be.kdg.SA.Land.domain.*;

import be.kdg.SA.Land.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Optional<Appointment> findByAppointmentId(UUID afspraakId) {
        return this.appointmentRepository.findByAppointmentId(afspraakId);
    }
    public Optional<Appointment> createAppointment(Appointment appointment) {
        try {
            return Optional.of(appointmentRepository.save(appointment));
        } catch (Exception e) {
            return Optional.empty();
        }
       }

    public List<Appointment> findAllAppointments() {
        return this.appointmentRepository.findAll();
    }

}




