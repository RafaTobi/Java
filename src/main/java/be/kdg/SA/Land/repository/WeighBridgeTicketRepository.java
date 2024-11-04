package be.kdg.sa.land.repository;

import be.kdg.sa.land.domain.Truck;
import be.kdg.sa.land.domain.WeighBridgeTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeighBridgeTicketRepository extends JpaRepository<WeighBridgeTicket, Long> {
    Optional<WeighBridgeTicket> findByTruckAndDepartureTimeIsNull(Truck truck);
}
