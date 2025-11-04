package be.kdg.sa.water.controller.api;

import be.kdg.sa.water.controller.dto.CaptainRequestDto;
import be.kdg.sa.water.controller.dto.DockEntryDto;
import be.kdg.sa.water.controller.dto.DockOperationDto;
import be.kdg.sa.water.domain.DockOperation;
import be.kdg.sa.water.service.DockOperationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/dockoperations")
public class DockOperationRestController {
    private final DockOperationService dockOperationService;
    private static final Logger logger = Logger.getLogger(DockOperationRestController.class.getName());

    public DockOperationRestController(DockOperationService dockOperationService) {
        this.dockOperationService = dockOperationService;
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('captain')")
    public ResponseEntity<DockOperationDto> createDockOperation(@RequestBody DockEntryDto dockEntryDto) {
        Jwt token = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("Captain " + token.getClaimAsString("name") + " with ship " + dockEntryDto.getVesselNumber() + " has just arrived in the dock.");
        logger.info("Starting dock operation.");
        DockOperation dockOperation = dockOperationService.createDockOperation(dockEntryDto.getPoReference(), dockEntryDto.getVesselNumber());
        DockOperationDto doDto = dockOperationService.mapToDto(dockOperation);

        return ResponseEntity.status(HttpStatus.CREATED).body(doDto);
    }

    @GetMapping("/operation-statuses")
    @PreAuthorize("hasAuthority('captain')")
    public ResponseEntity<String> getOperationStatuses(@RequestBody CaptainRequestDto captainRequestDto) {
        Jwt token = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("Captain " + token.getClaimAsString("name") + " with ship " + captainRequestDto.getVesselNumber() + " has requested his operation statuses.");
        boolean status = dockOperationService.getOperationStatuses(captainRequestDto);

        if (status) return ResponseEntity.ok("All operations have been completed!");
        else return ResponseEntity.ok("Not all mandatory operation are finished. Please wait until they are completed!");

    }
}
