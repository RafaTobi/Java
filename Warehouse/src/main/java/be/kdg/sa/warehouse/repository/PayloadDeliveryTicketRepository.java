package be.kdg.sa.warehouse.repository;

import be.kdg.sa.warehouse.domain.PayloadDeliveryTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PayloadDeliveryTicketRepository extends JpaRepository<PayloadDeliveryTicket, Long> {
    Optional<List<PayloadDeliveryTicket>> findPayloadDeliveryTicketsByMaterialIdOrderByTimeOfDeliveryAsc(long material_id);

    Optional<List<PayloadDeliveryTicket>> findPayloadDeliveryTicketsByWarehouseIdOrderByTimeOfDeliveryAsc(long warehouse_id);

    @Query("SELECT p FROM PayloadDeliveryTicket p WHERE p.warehouse.id = :warehouseId AND p.amount > 0 ORDER BY p.timeOfDelivery ASC")
    Optional<List<PayloadDeliveryTicket>> findPayloadDeliveryTicketsByWarehouseIdAAndAmountGreaterThanZeroOrderByTimeOfDeliveryAsc(long warehouseId);
}
