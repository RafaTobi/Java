package be.kdg.sa.water.service;

import be.kdg.sa.water.domain.InspectionOperation;
import be.kdg.sa.water.repository.InspectionOperationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InspectionOperationService {
    private final InspectionOperationRepository inspectionOperationRepository;

    public InspectionOperationService(InspectionOperationRepository inspectionOperationRepository) {
        this.inspectionOperationRepository = inspectionOperationRepository;
    }

    public InspectionOperation createNewIo(){
        InspectionOperation inspectionOperation = new InspectionOperation();
        inspectionOperation.setTimeScheduled(LocalDateTime.now());
        return inspectionOperationRepository.save(inspectionOperation);
    }

    public Optional<List<InspectionOperation>> getAllInspectionOperations() {
        return inspectionOperationRepository.findAllByOrderByTimeScheduledAsc();
    }

    public Optional<List<InspectionOperation>> getAllNonCompletedInspectionOperations(boolean completed) {
        return inspectionOperationRepository.findAllByCompleted(completed);
    }

    public void inspectLongestWaitingShip() {
        Optional<List<InspectionOperation>> optInspectionOperations = inspectionOperationRepository.findAllByOrderByTimeScheduledAscWhereNotCompleted();
        InspectionOperation inspectionOperation = optInspectionOperations.get().get(0); // Ik ga ervan uit dat er altijd 1 zal zijn
        inspectionOperation.setCompleted(true);
        inspectionOperationRepository.save(inspectionOperation);
    }

    public void inspectShipByIoId(long inspectionOrderId) {
        InspectionOperation inspectionOperation = inspectionOperationRepository.findById(inspectionOrderId).get();
        inspectionOperation.setCompleted(true);
        inspectionOperationRepository.save(inspectionOperation);
    }
}
