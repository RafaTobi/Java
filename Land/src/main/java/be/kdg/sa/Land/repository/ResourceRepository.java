package be.kdg.sa.land.repository;

import be.kdg.sa.land.domain.Resource;
import be.kdg.sa.land.domain.enums.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ResourceRepository extends JpaRepository<Resource, ResourceType> {
    Optional<Resource> findByName(ResourceType name);
}
