package be.kdg.sa.warehouse.controller.api;

import be.kdg.sa.warehouse.controller.dto.WarehouseDto;
import be.kdg.sa.warehouse.domain.Warehouse;
import be.kdg.sa.warehouse.service.WarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseRestController {
    private final WarehouseService warehouseService;
    private static final Logger logger = Logger.getLogger(WarehouseRestController.class.getName());

    public WarehouseRestController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping("/{id}/storage")
    public ResponseEntity<?> getWarehouseStorage(@PathVariable long id) {
        Optional<Warehouse> optWarehouse = warehouseService.findWarehouseById(id);
        if (optWarehouse.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Warehouse Not Found");
        return ResponseEntity.ok(optWarehouse.get().getStorage());
    }

    @GetMapping("/material/{materialId}/storage")
    public ResponseEntity<?> getWarehouseStorageByMaterial(@PathVariable long materialId) {
        List<WarehouseDto> warehouseDtos = new ArrayList<>();
        List<Warehouse> warehouses = warehouseService.findAllWarehousesStoragingMaterial(materialId);

        if (warehouses.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Warehouse With Material Found");

        double totalStorage = 0.0;
        for (Warehouse warehouse : warehouses) {
            totalStorage += warehouse.getStorage();
            warehouseDtos.add(warehouseService.mapToDto(warehouse));
        }

        String materialName = warehouses.get(0).getMaterial().getName();

        logger.info("De totale opslag van product: " + materialName + " is " + totalStorage + "kt.");
        logger.info("Verspreid over " + warehouses.size() + " warehouse(s).");
        return ResponseEntity.ok(warehouseDtos);
    }

    @GetMapping("/storage/check/{userUuid}") // Wordt opgeroepen via Land
    public ResponseEntity<Boolean> isStorageAvailable(@PathVariable UUID userUuid, @RequestParam String materialType){
        boolean isStorage = warehouseService.checkStorageCapacity(userUuid, materialType);
        return ResponseEntity.ok(isStorage);
    }
}
