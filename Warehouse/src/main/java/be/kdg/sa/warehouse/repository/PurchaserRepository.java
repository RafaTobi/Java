package be.kdg.sa.warehouse.repository;

import be.kdg.sa.warehouse.domain.Purchaser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaserRepository extends JpaRepository<Purchaser, UUID> {

}
