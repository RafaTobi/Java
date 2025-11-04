package be.kdg.sa.warehouse.repository;

import be.kdg.sa.warehouse.domain.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Optional<Warehouse> findWarehouseById(long warehouseId);

    Optional<List<Warehouse>> findWarehousesByMaterialId(long materialId);

    Optional<Warehouse> findWarehouseBySupplierUuidAndMaterialId(UUID supplier_uuid, long material_id);

    Optional<List<Warehouse>> findWarehousesBySupplierUuid(UUID supplier_uuid);

    Optional<Warehouse> findWarehouseBySupplierUuidAndMaterialName(UUID supplierUuid, String materialType);
}
