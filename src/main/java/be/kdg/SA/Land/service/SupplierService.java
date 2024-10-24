// SupplierService.java
package be.kdg.SA.Land.service;

import be.kdg.SA.Land.domain.Supplier;
import be.kdg.SA.Land.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public Supplier findOrCreateSupplier(Supplier supplier) {
        Optional<Supplier> existingSupplier = supplierRepository.findById(supplier.getId());
        return existingSupplier.orElseGet(() -> supplierRepository.save(supplier));
    }

    public List<Supplier> findAllSuppliers() {
        return this.supplierRepository.findAll();
    }


}