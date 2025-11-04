package be.kdg.sa.water.controller.api;

import be.kdg.sa.water.domain.InspectionOperation;
import be.kdg.sa.water.service.InspectionOperationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/inspection-operations")
public class InspectionOperationRestController {
    private final InspectionOperationService inspectionOperationService;
    private static final Logger logger = Logger.getLogger(InspectionOperationService.class.getName());

    public InspectionOperationRestController(InspectionOperationService inspectionOperationService) {
        this.inspectionOperationService = inspectionOperationService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('inspector')")
    public ResponseEntity<?> getAllInspectionOperations(){
        Optional<List<InspectionOperation>> optInspectionOperations = inspectionOperationService.getAllInspectionOperations();
        if (optInspectionOperations.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Inspection Operations found!");
        return ResponseEntity.ok(optInspectionOperations.get());
    }

    @GetMapping("/status")
    @PreAuthorize("hasAuthority('inspector')")
    public ResponseEntity<?> getAllInspectionOperationsByCompleted(@RequestParam boolean completed){
        Optional<List<InspectionOperation>> optInspectionOperations = inspectionOperationService.getAllNonCompletedInspectionOperations(completed);
        if (optInspectionOperations.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No pending Inspection Operations found!");
        return ResponseEntity.ok(optInspectionOperations.get());
    }

    @PutMapping("/inspect/oldest")
    @PreAuthorize("hasAuthority('inspector')")
    public ResponseEntity<String> inspectLongestWaitingShip(){
        logger.info("Inspection on longest waiting ship has started.");
        inspectionOperationService.inspectLongestWaitingShip();
        return ResponseEntity.ok("The longest waiting ship has successfully been inspected!");
    }

    @PutMapping("/inspect")
    @PreAuthorize("hasAuthority('inspector')")
    public ResponseEntity<String> inspectShipByIoId(@RequestParam long inspectionOrderId){
        logger.info("Inspection order with id: " + inspectionOrderId + " has started.");
        inspectionOperationService.inspectShipByIoId(inspectionOrderId);
        return ResponseEntity.ok("Ship inspection was successful!");
    }
}
