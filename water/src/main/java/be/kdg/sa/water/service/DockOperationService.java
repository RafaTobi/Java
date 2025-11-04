package be.kdg.sa.water.service;

import be.kdg.sa.water.controller.dto.CaptainRequestDto;
import be.kdg.sa.water.controller.dto.DockOperationDto;
import be.kdg.sa.water.domain.DockOperation;
import be.kdg.sa.water.repository.DockOperationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class DockOperationService {
    private final DockOperationRepository dockOperationRepository;
    private final InspectionOperationService inspectionOperationService;
    private final BunkerOperationService bunkerOperationService;
    private static final Logger logger = Logger.getLogger(DockOperationService.class.getName());

    public DockOperationService(DockOperationRepository dockOperationRepository, InspectionOperationService inspectionOperationService, BunkerOperationService bunkerOperationService) {
        this.dockOperationRepository = dockOperationRepository;
        this.inspectionOperationService = inspectionOperationService;
        this.bunkerOperationService = bunkerOperationService;
    }

    public DockOperation createDockOperation(UUID poReference, String boatNumber){
        DockOperation dockOperation = new DockOperation();
        dockOperation.setPurchaseOrderReference(poReference);
        dockOperation.setArrival(LocalDateTime.now());
        logger.info("Now planning new bunker operation.");
        dockOperation.setBunkerOperation(bunkerOperationService.createNewBo());
        logger.info("Now planning new inspection operation.");
        dockOperation.setInspectionOperation(inspectionOperationService.createNewIo());
        dockOperation.setBoat(boatNumber);

        return dockOperationRepository.save(dockOperation);
    }

    public DockOperationDto mapToDto(DockOperation dockOperation) {
        DockOperationDto doDto = new DockOperationDto();
        doDto.setId(dockOperation.getId());
        doDto.setPurchaseOrderReference(dockOperation.getPurchaseOrderReference());
        doDto.setVesselNumber(dockOperation.getVesselNumber());
        doDto.setArrival(dockOperation.getArrival());
        doDto.setDeparture(dockOperation.getDeparture());
        doDto.setBunkerOperationId(dockOperation.getBunkerOperation().getId());
        doDto.setInspectionOperationId(dockOperation.getInspectionOperation().getId());

        return doDto;
    }

    public boolean getOperationStatuses(CaptainRequestDto captainRequestDto) {
        Optional<DockOperation> optDockOperation = dockOperationRepository.findDockOperationByPurchaseOrderReferenceAndVesselNumber(captainRequestDto.getPoReference(), captainRequestDto.getVesselNumber());
        DockOperation dockOperation = optDockOperation.get();

        if(!dockOperation.getBunkerOperation().isCompleted()) logger.warning("Bunker operation has not yet been completed.");
        if(!dockOperation.getInspectionOperation().isCompleted()) logger.warning("Inspection operation has not yet been completed.");
        return dockOperation.getBunkerOperation().isCompleted() && dockOperation.getInspectionOperation().isCompleted();
    }
}
