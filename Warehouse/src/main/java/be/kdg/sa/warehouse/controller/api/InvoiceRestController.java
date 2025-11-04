package be.kdg.sa.warehouse.controller.api;

import be.kdg.sa.warehouse.controller.dto.InvoiceDto;
import be.kdg.sa.warehouse.domain.Invoice;
import be.kdg.sa.warehouse.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceRestController {
    private final InvoiceService invoiceService;
    private static final Logger logger = Logger.getLogger(InvoiceRestController.class.getName());

    public InvoiceRestController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/generate")
    public void generateDailyInvoice() { // test het uitrekenen van invoices
        logger.info("Invoice calculation started!");
        invoiceService.calculateInvoice();
    }

    @GetMapping("/commission/{supplierUuid}")
    public ResponseEntity<Double> getCommissionForSupplier(@PathVariable UUID supplierUuid) {
        logger.info("Commission calculation started!");
        double commission = invoiceService.calculateTodaysCommission(supplierUuid);
        return ResponseEntity.ok(commission);
    }

    @GetMapping("/{supplierUuid}")
    public ResponseEntity<?> getAllForSupplier(@PathVariable UUID supplierUuid, @RequestParam LocalDate date) {
        Optional<List<Invoice>> optInvoices = invoiceService.getAllInvoicesBySupplierUuid(supplierUuid, date);
        if (optInvoices.get().isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Invoices found for supplier on day: " + date);

        List<InvoiceDto> invoiceDtos = new ArrayList<>();
        for (Invoice invoice : optInvoices.get()) {
            invoiceDtos.add(invoiceService.mapToDto(invoice));
        }

        return ResponseEntity.ok(invoiceDtos);
    }
}
