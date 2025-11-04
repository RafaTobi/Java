package be.kdg.sa.warehouse.repository;


import be.kdg.sa.warehouse.domain.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SupplierRepository extends JpaRepository<Supplier, UUID> {

}
