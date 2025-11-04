package be.kdg.sa.water.repository;

import be.kdg.sa.water.domain.DockOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DockOperationRepository extends JpaRepository<DockOperation, Long> {
    Optional<DockOperation> findDockOperationByPurchaseOrderReferenceAndVesselNumber(UUID purchaseOrderReference, String vesselNumber);
}
