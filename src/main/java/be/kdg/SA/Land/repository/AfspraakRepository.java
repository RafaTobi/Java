package be.kdg.SA.Land.repository;
import be.kdg.SA.Land.domain.Afspraak;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AfspraakRepository extends JpaRepository<Afspraak, UUID> {
    Optional<Afspraak> findByAfspraakId(UUID afspraakId);

}
