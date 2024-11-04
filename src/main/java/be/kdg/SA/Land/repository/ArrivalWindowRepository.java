package be.kdg.sa.land.repository;


import be.kdg.sa.land.domain.ArrivalWindow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface ArrivalWindowRepository extends JpaRepository<ArrivalWindow, UUID> {
    Optional<ArrivalWindow> findWindowById(UUID appointmentId);

}

