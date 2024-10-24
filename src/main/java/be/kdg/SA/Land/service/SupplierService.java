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

    public Supplier findSupplier(Supplier supplier) {
        Optional<Supplier> existingSupplier = supplierRepository.findByName(supplier.getName());
        if (existingSupplier.isPresent()) {
            return existingSupplier.get();
        } else {
            throw new IllegalArgumentException("Supplier does not exist in the database");
        }
    }

    public List<Supplier> findAllSuppliers() {
        return this.supplierRepository.findAll();
    }


}