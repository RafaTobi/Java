package be.kdg.SA.Land.repository;

import be.kdg.SA.Land.domain.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
    Optional<Supplier> findByName(String name);
    Optional<Supplier> findById(Long id);
}