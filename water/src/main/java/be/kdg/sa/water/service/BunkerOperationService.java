package be.kdg.sa.water.service;

import be.kdg.sa.water.domain.BunkerOperation;
import be.kdg.sa.water.repository.BunkerOperationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class BunkerOperationService {
    private final BunkerOperationRepository bunkerOperationRepository;
    private static final Logger logger = Logger.getLogger(BunkerOperationService.class.getName());

    public BunkerOperationService(BunkerOperationRepository bunkerOperationRepository) {
        this.bunkerOperationRepository = bunkerOperationRepository;
    }

    public BunkerOperation createNewBo() {
        BunkerOperation bunkerOperation = new BunkerOperation();
        bunkerOperation.setTimeScheduled(LocalDateTime.now());
        return bunkerOperationRepository.save(bunkerOperation);
    }

    public void planOperation(long bunkerOperationId) {
        Optional<BunkerOperation> optBunkerOperation = bunkerOperationRepository.findById(bunkerOperationId);
        if (optBunkerOperation.isPresent()) {
            BunkerOperation bunkerOperation = optBunkerOperation.get();
            bunkerOperation.setCompleted(true);
            bunkerOperationRepository.save(bunkerOperation);
        }
    }

    @Scheduled(fixedRate = 14400000) //elke 4 uur wordt de oudste Bunkeroperation vervuld
    public void planOperationAutomatically() {
        Optional<List<BunkerOperation>> optBunkerOperations = bunkerOperationRepository.findAllByOrderByTimeScheduledAsc();
        if (optBunkerOperations.isPresent()) {
            BunkerOperation bunkerOperation = optBunkerOperations.get().get(0);
            bunkerOperation.setCompleted(true);
            logger.info("Bunkeroperation with id " + bunkerOperation.getId() + " has been completed.");
            bunkerOperationRepository.save(bunkerOperation);
        }
    }
}
