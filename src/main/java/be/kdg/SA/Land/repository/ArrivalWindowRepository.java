package be.kdg.SA.Land.repository;


import be.kdg.SA.Land.domain.Appointment;
import be.kdg.SA.Land.domain.ArrivalWindow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface ArrivalWindowRepository extends JpaRepository<ArrivalWindow, UUID> {
    Optional<ArrivalWindow> findWindowById(UUID appointmentId);

}

