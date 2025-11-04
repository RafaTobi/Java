package be.kdg.sa.warehouse.service;

import be.kdg.sa.warehouse.controller.api.MaterialRestController;
import be.kdg.sa.warehouse.controller.dto.WarehouseDto;
import be.kdg.sa.warehouse.domain.Material;
import be.kdg.sa.warehouse.domain.Supplier;
import be.kdg.sa.warehouse.domain.Warehouse;
import be.kdg.sa.warehouse.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final SupplierService supplierService;
    private final MaterialService materialService;
    @Value("${warehouse.max.capacity}")
    private double maxStorageCapacity;
    private static final Logger logger = Logger.getLogger(WarehouseService.class.getName());


    public WarehouseService(WarehouseRepository warehouseRepository, SupplierService supplierService, MaterialService materialService) {
        this.warehouseRepository = warehouseRepository;
        this.supplierService = supplierService;
        this.materialService = materialService;
    }

    public Optional<Warehouse> findWarehouseById(long warehouseId) {
        return warehouseRepository.findWarehouseById(warehouseId);
    }

    public List<Warehouse> findAllWarehousesStoragingMaterial(long materialId) {
        Optional<List<Warehouse>> warehousesByMaterialId = warehouseRepository.findWarehousesByMaterialId(materialId);
        return warehousesByMaterialId.orElse(null);
    }

    public Warehouse findAllWarehousesFromSupplierStoragingMaterial(UUID sellerPartyUuid, long materialId) {
        Optional<Warehouse> optWarehouse = warehouseRepository.findWarehouseBySupplierUuidAndMaterialId(sellerPartyUuid, materialId);
        if (optWarehouse.isPresent()) {
            return optWarehouse.get();
        } else {
            Warehouse newWarehouse = new Warehouse();
            Supplier supplier = new Supplier();
            newWarehouse.setSupplier(supplier);

            Material material = new Material();
            newWarehouse.setMaterial(material);

            return warehouseRepository.save(newWarehouse);
        }
    }

    public void retrieveMaterialsFromWarehouse(long warehouseId, double quantity) {
        Optional<Warehouse> optWarehouse = warehouseRepository.findById(warehouseId);
        if (optWarehouse.isPresent()) {
            Warehouse warehouse = optWarehouse.get();
            warehouse.setStorage(warehouse.getStorage() - quantity);
            warehouseRepository.save(warehouse);
        }
    }

    public void addMaterialsToWarehouse(long warehouseId, double quantity) {
        Optional<Warehouse> optWarehouse = warehouseRepository.findById(warehouseId);
        if (optWarehouse.isPresent()) {
            Warehouse warehouse = optWarehouse.get();
            warehouse.setStorage(warehouse.getStorage() + quantity);
            warehouseRepository.save(warehouse);
        }
    }

    public List<Warehouse> findAllWarehousesBySupplierId(UUID supplierUuid) {
        Optional<List<Warehouse>> optWarehouses = warehouseRepository.findWarehousesBySupplierUuid(supplierUuid);
        return optWarehouses.orElse(null);
    }

    public WarehouseDto mapToDto(Warehouse warehouse) {
        WarehouseDto warehouseDto = new WarehouseDto();
        warehouseDto.setId(warehouse.getId());
        warehouseDto.setStorage(warehouse.getStorage());
        warehouseDto.setMaterialId(warehouse.getMaterial().getId());
        warehouseDto.setSupplierUuid(warehouse.getSupplier().getUuid());

        return warehouseDto;
    }

    public Optional<Warehouse> findWarehouseFromSupplierByMaterialType(UUID supplierUuid, String materialType) {
        return warehouseRepository.findWarehouseBySupplierUuidAndMaterialName(supplierUuid, materialType);
    }

    public Warehouse createWarehouse(UUID supplierUuid, String materialType) {
        Warehouse newWarehouse = new Warehouse();
        Supplier supplier = supplierService.getSupplierById(supplierUuid).get();
        Material material = materialService.getMaterialByName(materialType);

        newWarehouse.setSupplier(supplier);
        newWarehouse.setMaterial(material);
        newWarehouse.setStorage(0);
        return warehouseRepository.save(newWarehouse);
    }

    public boolean checkStorageCapacity(UUID userUuid, String materialType) {
        Warehouse warehouse;
        Optional<Warehouse> optWarehouse = warehouseRepository.findWarehouseBySupplierUuidAndMaterialName(userUuid, materialType);
        if (optWarehouse.isEmpty()) {
            logger.warning("No warehouse found. Creating new one!");
            warehouse = createWarehouse(userUuid, materialType);
        } else warehouse = optWarehouse.get();


        boolean isCapacity = warehouse.getStorage() < maxStorageCapacity;
        if (isCapacity) {
            logger.info("There is enough free capacity in this warehouse.");
        } else {
            logger.warning("There is not enough free capacity in this warehouse.");
        }
        return isCapacity;
    }
}
