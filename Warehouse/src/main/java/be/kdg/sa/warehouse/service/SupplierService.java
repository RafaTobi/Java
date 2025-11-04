package be.kdg.sa.warehouse.service;

import be.kdg.sa.warehouse.controller.dto.SupplierDto;
import be.kdg.sa.warehouse.domain.Supplier;
import be.kdg.sa.warehouse.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public Optional<Supplier> getSupplierById(UUID id) {
        return supplierRepository.findById(id);
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public SupplierDto mapToDto(Supplier supplier) {
        SupplierDto supplierDto = new SupplierDto();

        supplierDto.setUuid(supplier.getUuid());
        supplierDto.setName(supplier.getName());
        supplierDto.setAddress(supplier.getAddress());
        return supplierDto;
    }

    public Supplier mapToSupplier(SupplierDto supplierDto) {
        Supplier supplier = new Supplier();

        supplier.setUuid(supplierDto.getUuid());
        supplier.setName(supplierDto.getName());
        supplier.setAddress(supplierDto.getAddress());
        return supplier;
    }

    public Supplier createSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }
}
