package be.kdg.sa.land.controller.api;

import be.kdg.sa.land.controller.dto.WeighingOperationDto;
import be.kdg.sa.land.service.WeighingOperationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weighing")
public class WeighingOperationController {

    private final WeighingOperationService weighingOperationService;

    public WeighingOperationController(WeighingOperationService weighingOperationService) {
        this.weighingOperationService = weighingOperationService;
    }

    @PostMapping("/operation")
    public ResponseEntity<String> handleWeighingOperation(@RequestBody WeighingOperationDto weighingOperationDto) {
        weighingOperationService.processWeighingOperation(weighingOperationDto);
        return ResponseEntity.ok("Weighing operation processed successfully.");
    }
}
