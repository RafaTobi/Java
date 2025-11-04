package be.kdg.sa.water.repository;

import be.kdg.sa.water.domain.InspectionOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InspectionOperationRepository extends JpaRepository<InspectionOperation, Long> {
    @Query("SELECT i FROM InspectionOperation i WHERE i.completed = false ORDER BY i.timeScheduled ASC")
    Optional<List<InspectionOperation>> findAllByOrderByTimeScheduledAscWhereNotCompleted();

    @Query("SELECT i FROM InspectionOperation i ORDER BY i.timeScheduled ASC")
    Optional<List<InspectionOperation>> findAllByOrderByTimeScheduledAsc();

    Optional<List<InspectionOperation>> findAllByCompleted(boolean completed);
}
