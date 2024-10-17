package be.kdg.SA.Land.repository;
import be.kdg.SA.Land.domain.Appointment;
import be.kdg.SA.Land.domain.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    @Query("" +
            "SELECT a FROM Appointment a WHERE a.appointmentId = :appointmentId")
    Optional<Appointment> findByAppointmentId(UUID appointmentId);
    @Query("" +
            "SELECT a FROM Appointment a WHERE a.truck = :truck")
    Optional<List<Appointment>> findAppointmentByTruck(Truck truck);

}
