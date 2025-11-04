package be.kdg.sa.warehouse.service;

import be.kdg.sa.warehouse.controller.api.MaterialRestController;
import be.kdg.sa.warehouse.domain.Material;
import be.kdg.sa.warehouse.repository.MaterialRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class MaterialService {
    private final MaterialRepository materialRepository;
    private static final Logger logger = Logger.getLogger(MaterialService.class.getName());

    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public Optional<Material> getMaterial(long materialId) {
        Optional<Material> optMaterial = materialRepository.findById(materialId);
        return optMaterial;
    }

    public Material updateStorageCost(long materialId, double newStorageCost) {
        Optional<Material> optMaterial = materialRepository.findById(materialId);
        if (optMaterial.isPresent()) {
            Material material = optMaterial.get();
            material.setStorageCost(newStorageCost);
            return materialRepository.save(material);
        }

        logger.warning("Material does not exist!");
        return null;
    }

    public Material updateSellingPrice(long materialId, double newSellingPrice) {
        Optional<Material> optMaterial = materialRepository.findById(materialId);
        if (optMaterial.isPresent()) {
            Material material = optMaterial.get();
            material.setSellingPrice(newSellingPrice);
            return materialRepository.save(material);
        }

        logger.warning("Material does not exist!");
        return null;
    }

    public Material getMaterialByName(String materialName) {
        Optional<Material> material = materialRepository.findMaterialByNameIgnoreCase(materialName);
        return material.orElse(null);
    }
}
