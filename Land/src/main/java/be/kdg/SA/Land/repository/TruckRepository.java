package be.kdg.sa.land.repository;

import be.kdg.sa.land.domain.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TruckRepository extends JpaRepository<Truck, UUID> {
    Optional<Truck> findTruckByLicenseplate(String licenseplate);

    long countByOnSiteTrue();

}
