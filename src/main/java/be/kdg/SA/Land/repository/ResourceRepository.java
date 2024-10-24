package be.kdg.SA.Land.repository;

import be.kdg.SA.Land.domain.Resource;
import be.kdg.SA.Land.domain.enums.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ResourceRepository extends JpaRepository<Resource, ResourceType> {
    Optional<Resource> findByName(ResourceType name);
}
