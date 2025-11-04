package be.kdg.sa.warehouse.repository;

import be.kdg.sa.warehouse.domain.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query(value = "SELECT * FROM invoice i WHERE i.supplier_uuid = :supplierUuid AND DATE(i.date) = :date", nativeQuery = true)
    Optional<List<Invoice>> findAllBySupplierUuidAndDate(UUID supplierUuid, LocalDate date);
}
