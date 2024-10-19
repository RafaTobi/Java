package be.kdg.SA.Land.repository;

import be.kdg.SA.Land.domain.Resource;
import be.kdg.SA.Land.domain.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ResourceRepository extends JpaRepository<Resource, UUID> {
    Optional<Resource> findByName(String name);
}
