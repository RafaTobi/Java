package be.kdg.sa.warehouse.service;

import be.kdg.sa.warehouse.domain.*;
import be.kdg.sa.warehouse.repository.InvoiceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
class InvoiceServiceUnitTest {
    @Autowired
    private InvoiceService invoiceService;
    @MockBean
    private InvoiceRepository invoiceRepository;
    @MockBean
    private SupplierService supplierService;
    @MockBean
    private WarehouseService warehouseService;
    @MockBean
    private PayloadDeliveryTicketService pdtService;
    @MockBean
    private Clock clock;

    @Test
    public void calculateInvoiceCreatesInvoicePerSupplier() {
        //Arrange
        var supplierUuid1 = UUID.fromString("aa13794c-d363-4aa6-8370-529af93ee1e5");
        var supplierUuid2 = UUID.fromString("24fb6602-9b59-474c-a46c-820a1b50adab");

        LocalDateTime fixedDateTime = LocalDateTime.of(2024, 10, 26, 9, 0, 0);
        Clock fixedClock = Clock.fixed(fixedDateTime.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());

        given(clock.instant()).willReturn(fixedClock.instant());
        given(clock.getZone()).willReturn(fixedClock.getZone());

        given(supplierService.getAllSuppliers())
                .willReturn(List.of(
                        new Supplier(
                                supplierUuid1,
                                "Name Um",
                                "Address 1",
                                List.of(
                                        new Warehouse(
                                                1,
                                                20,
                                                new Material(
                                                        2,
                                                        "Ijzererts",
                                                        5,
                                                        110
                                                ))
                                )),
                        new Supplier(
                                supplierUuid2,
                                "Name Dois",
                                "Address 2",
                                List.of(
                                        new Warehouse(
                                                2,
                                                20,
                                                new Material(
                                                        1,
                                                        "Gips",
                                                        1,
                                                        13
                                                ))
                                ))
                ));

        given(warehouseService.findAllWarehousesBySupplierId(supplierUuid1))
                .willReturn(
                        List.of(
                                new Warehouse(
                                        1,
                                        20,
                                        new Material(
                                                2,
                                                "Ijzererts",
                                                5,
                                                110
                                        ))
                        ));

        given(warehouseService.findAllWarehousesBySupplierId(supplierUuid2))
                .willReturn(
                        List.of(
                                new Warehouse(
                                        2,
                                        25,
                                        new Material(
                                                1,
                                                "Gips",
                                                1,
                                                13
                                        ))
                        ));

        given(pdtService.getPayloadDeliveryTicketsByWarehouseId(1))
                .willReturn(
                        List.of(
                                new PayloadDeliveryTicket(
                                        1,
                                        20,
                                        LocalDateTime.of(2024, 10, 11, 16, 30, 30),
                                        5
                                )
                        )
                );

        given(pdtService.getPayloadDeliveryTicketsByWarehouseId(2))
                .willReturn(
                        List.of(
                                new PayloadDeliveryTicket(
                                        2,
                                        25,
                                        LocalDateTime.of(2024, 10, 16, 16, 30, 30),
                                        1
                                )
                        )
                );

        //Act
        invoiceService.calculateInvoice();

        ArgumentCaptor<Invoice> argumentCaptor = ArgumentCaptor.forClass(Invoice.class);

        //Assert
        verify(invoiceRepository, times(2)).save(argumentCaptor.capture());

        var invoices = argumentCaptor.getAllValues();
        assertEquals(2, invoices.size());

        // Invoice supplier 1
        assertEquals(1500.0, invoices.get(0).getPrice()); // prijs 5 * dagen 15 * 20 kt = 1500
        assertEquals(LocalDateTime.of(2024, 10, 26, 9, 0, 0), invoices.get(0).getDate());
        assertEquals(supplierUuid1, invoices.get(0).getSupplier().getUuid());

        // Invoice supplier 2
        assertEquals(250.0, invoices.get(1).getPrice()); // prijs 1 * dagen 10 * 25 kt = 250
        assertEquals(LocalDateTime.of(2024, 10, 26, 9, 0, 0), invoices.get(1).getDate());
        assertEquals(supplierUuid2, invoices.get(1).getSupplier().getUuid());
    }

    @Test
    public void calculateInvoiceForSupplierWithNoWarehousesShouldCreateInvoiceWithPriceZero() {
        //Arrange
        var supplierUuid = UUID.fromString("aa13794c-d363-4aa6-8370-529af93ee1e5");

        LocalDateTime fixedDateTime = LocalDateTime.of(2024, 10, 26, 9, 0, 0);
        Clock fixedClock = Clock.fixed(fixedDateTime.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());

        given(clock.instant()).willReturn(fixedClock.instant());
        given(clock.getZone()).willReturn(fixedClock.getZone());

        given(supplierService.getAllSuppliers())
                .willReturn(List.of(
                        new Supplier(
                                supplierUuid,
                                "Second Supp",
                                "Address",
                                List.of())
                ));

        //Act
        invoiceService.calculateInvoice();
        ArgumentCaptor<Invoice> argumentCaptor = ArgumentCaptor.forClass(Invoice.class);

        //Assert
        verify(pdtService, times(0)).getPayloadDeliveryTicketsByWarehouseId(any(Long.class));
        verify(invoiceRepository, times(1)).save(argumentCaptor.capture());

        var invoice = argumentCaptor.getValue();
        assertNotNull(invoice);
        assertEquals(0.0, invoice.getPrice());
        assertEquals(supplierUuid, invoice.getSupplier().getUuid());
        assertEquals(LocalDateTime.of(2024, 10, 26, 9, 0, 0), invoice.getDate());
    }

    @Test
    public void calculateInvoiceWhenNoSuppliersExistShouldNotCreateInvoice() {
        //Arrange
        given(supplierService.getAllSuppliers())
                .willReturn(List.of());

        //Act
        invoiceService.calculateInvoice();

        //Assert
        verify(invoiceRepository, times(0)).save(any(Invoice.class));
    }
}