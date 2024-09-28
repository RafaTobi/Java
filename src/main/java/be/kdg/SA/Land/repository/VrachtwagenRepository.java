package be.kdg.SA.Land.repository;

import be.kdg.SA.Land.domain.Vrachtwagen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VrachtwagenRepository extends JpaRepository<Vrachtwagen, UUID> {
    public Optional<Vrachtwagen> findVrachtwagenByNummerplaat(String nummerplaat);
}
