package be.kdg.sa.warehouse.repository;

import be.kdg.sa.warehouse.domain.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    Optional<Material> findMaterialByNameIgnoreCase(String materialName);
}
