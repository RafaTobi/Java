package be.kdg.SA.Land.repository;
import be.kdg.SA.Land.domain.Afspraak;
import be.kdg.SA.Land.domain.Vrachtwagen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AfspraakRepository extends JpaRepository<Afspraak, UUID> {
    Optional<Afspraak> findByAfspraakId(UUID afspraakId);
    Optional<List<Afspraak>> findAfspraakByVrachtwagen(Vrachtwagen vrachtwagen);
}
