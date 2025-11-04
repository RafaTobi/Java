package be.kdg.sa.water.repository;

import be.kdg.sa.water.domain.BunkerOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BunkerOperationRepository extends JpaRepository<BunkerOperation, Long> {
    @Query("SELECT b FROM BunkerOperation b WHERE b.completed = false ORDER BY b.timeScheduled ASC")
    Optional<List<BunkerOperation>> findAllByOrderByTimeScheduledAsc();
}
