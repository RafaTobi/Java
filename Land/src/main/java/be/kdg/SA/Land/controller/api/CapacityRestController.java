package be.kdg.sa.land.controller.api;

import be.kdg.sa.land.controller.dto.CapacityCheckDto;
import be.kdg.sa.land.service.CheckCapacityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/capacity")
public class CapacityRestController {
    private final CheckCapacityService checkCapacityService;

    public CapacityRestController(CheckCapacityService checkCapacityService) {
        this.checkCapacityService = checkCapacityService;
    }

    /**
     * Endpoint to check warehouse storage capacity.
     * @param capacityCheckDto DTO containing UUID of the user and material type.
     * @return ResponseEntity with true if capacity is available, otherwise false.
     */
    @PostMapping("/check")
    public ResponseEntity<Boolean> checkCapacity(@RequestBody CapacityCheckDto capacityCheckDto) {
        boolean isCapacityAvailable = checkCapacityService.checkStorageCapacity(capacityCheckDto);
        return ResponseEntity.ok(isCapacityAvailable);
    }
}
