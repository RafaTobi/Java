package be.kdg.sa.water.controller.api;

import be.kdg.sa.water.service.BunkerOperationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/bunker-operations")
public class BunkerOperationRestController {
    private final BunkerOperationService bunkerOperationService;

    public BunkerOperationRestController(BunkerOperationService bunkerOperationService) {
        this.bunkerOperationService = bunkerOperationService;
    }

    @PostMapping("/plan")
    @PreAuthorize("hasAuthority('bunker_officer')")
    public ResponseEntity<String> planBunkerOperation(@RequestParam long bunkerOperationId){
        bunkerOperationService.planOperation(bunkerOperationId);
        return ResponseEntity.ok().body("Successfully planned Bunker Operation");
    }
}
