package be.kdg.sa.warehouse.service;

import be.kdg.sa.warehouse.controller.api.WarehouseRestController;
import be.kdg.sa.warehouse.controller.dto.InvoiceDto;
import be.kdg.sa.warehouse.domain.Invoice;
import be.kdg.sa.warehouse.domain.PayloadDeliveryTicket;
import be.kdg.sa.warehouse.domain.Supplier;
import be.kdg.sa.warehouse.domain.Warehouse;
import be.kdg.sa.warehouse.repository.InvoiceRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final SupplierService supplierService;
    private final PayloadDeliveryTicketService pdtService;
    private final WarehouseService warehouseService;
    private final PurchaseOrderService purchaseOrderService;
    private static final Logger logger = Logger.getLogger(InvoiceService.class.getName());
    private final Clock clock; // Nodig voor Unit testing

    public InvoiceService(InvoiceRepository invoiceRepository, SupplierService supplierService, PayloadDeliveryTicketService pdtService, WarehouseService warehouseService, PurchaseOrderService purchaseOrderService, Clock clock) {
        this.invoiceRepository = invoiceRepository;
        this.supplierService = supplierService;
        this.pdtService = pdtService;
        this.warehouseService = warehouseService;
        this.purchaseOrderService = purchaseOrderService;
        this.clock = clock;
    }

    @Scheduled(cron = "0 0 9 * * *")
    public void calculateInvoice() {
        double storageCost;
        double amountOfTons;
        long daysInWarehouse;
        List<Supplier> allSuppliers = supplierService.getAllSuppliers();

        for (Supplier supplier : allSuppliers) {
            double price = 0;
            double commissionPrice = 0;
            Invoice invoice = new Invoice();

            for (Warehouse warehouse : warehouseService.findAllWarehousesBySupplierId(supplier.getUuid())) {
                List<PayloadDeliveryTicket> pdts = pdtService.getPayloadDeliveryTicketsByWarehouseId(warehouse.getId());
                for (PayloadDeliveryTicket pdt : pdts) {
                    daysInWarehouse = daysUntilToday(pdt.getTimeOfDelivery().toLocalDate());
                    storageCost = pdt.getStorageCost();
                    amountOfTons = pdt.getAmount();
                    price += (storageCost * daysInWarehouse * amountOfTons);
                }
            }

            commissionPrice = calculateTodaysCommission(supplier.getUuid());
            price += commissionPrice;

            invoice.setDate(LocalDateTime.now(clock));
            invoice.setPrice(price);
            invoice.setSupplier(supplier);

            logger.info("Total price for supplier: " + supplier.getUuid() + " today is " + price);
            invoiceRepository.save(invoice);
        }
    }

    public long daysUntilToday(LocalDate dayOfDelivery){
        return ChronoUnit.DAYS.between(dayOfDelivery, LocalDateTime.now(clock).toLocalDate());
    }

    public double calculateTodaysCommission(UUID supplierUuid) {
        return purchaseOrderService.calculateTodaysCommission(supplierUuid);
    }

    public Optional<List<Invoice>> getAllInvoicesBySupplierUuid(UUID supplierUuid, LocalDate date) {
        return invoiceRepository.findAllBySupplierUuidAndDate(supplierUuid, date);
    }

    public InvoiceDto mapToDto(Invoice invoice) {
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setId(invoice.getId());
        invoiceDto.setDate(invoice.getDate());
        invoiceDto.setPrice(invoice.getPrice());
        invoiceDto.setSupplierUuid(invoice.getSupplier().getUuid());
        return invoiceDto;
    }
}
