package be.kdg.sa.warehouse.repository;

import be.kdg.sa.warehouse.domain.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, UUID> {
    Optional<List<PurchaseOrder>> findPurchaseOrdersByOrderFulfilled(boolean orderFulfilled);

    @Query("SELECT p FROM PurchaseOrder p JOIN FETCH p.orderLines WHERE p.referenceUuid = :referenceUuid")
    Optional<PurchaseOrder> findPurchaseOrderByReferenceUuidWithOrderLines(@Param("referenceUuid") UUID poReference);

    @Query("SELECT p FROM PurchaseOrder p JOIN FETCH p.orderLines WHERE p.referenceUuid = :referenceUuid AND p.vesselNumber = :vesselNumber")
    Optional<PurchaseOrder> findPurchaseOrderByReferenceUuidAndVesselNumberWithOrderLines(@Param("referenceUuid") UUID poReference, String vesselNumber);

    Optional<List<PurchaseOrder>> findPurchaseOrdersBySellerPartyUuidAndFulfillDateAfter(UUID sellerPartyUuid, LocalDateTime fulfillDate);
}
