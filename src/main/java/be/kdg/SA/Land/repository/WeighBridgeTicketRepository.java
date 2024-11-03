package be.kdg.SA.Land.repository;

import be.kdg.SA.Land.domain.Truck;
import be.kdg.SA.Land.domain.WeighBridgeTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeighBridgeTicketRepository extends JpaRepository<WeighBridgeTicket, Long> {
    Optional<WeighBridgeTicket> findByTruckAndDepartureTimeIsNull(Truck truck);
}
