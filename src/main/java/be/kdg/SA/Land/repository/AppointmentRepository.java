package be.kdg.SA.Land.repository;
import be.kdg.SA.Land.domain.Appointment;
import be.kdg.SA.Land.domain.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    Optional<Appointment> findByAfspraakId(UUID afspraakId);
    Optional<List<Appointment>> findAfspraakByVrachtwagen(Truck truck);
}
