package be.kdg.sa.warehouse.controller.api;

import be.kdg.sa.warehouse.domain.Material;
import be.kdg.sa.warehouse.service.MaterialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/materials")
public class MaterialRestController {
    private final MaterialService materialService;
    private static final Logger logger = Logger.getLogger(MaterialRestController.class.getName());

    public MaterialRestController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Material> getMaterial(@PathVariable long id) {
        Optional<Material> material = materialService.getMaterial(id);
        return material.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/storage-cost")
    public ResponseEntity<Material> updateStorageCost(@PathVariable long id, @RequestParam double storageCost) {
        Material material = materialService.updateStorageCost(id, storageCost);
        if (material == null) return ResponseEntity.notFound().build();
        logger.info("Updated storage cost from " + material.getName() + " to " + storageCost);
        return ResponseEntity.ok(material);
    }

    @PutMapping("/{id}/selling-price")
    public ResponseEntity<Material> updateSellingPrice(@PathVariable long id, @RequestParam double sellingPrice) {
        Material material = materialService.updateSellingPrice(id, sellingPrice);
        if (material == null) return ResponseEntity.notFound().build();
        logger.info("Updated selling price from " + material.getName() + " to " + sellingPrice);
        return ResponseEntity.ok(material);
    }
}
