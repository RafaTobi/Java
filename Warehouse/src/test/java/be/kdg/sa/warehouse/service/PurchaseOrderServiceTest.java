package be.kdg.sa.warehouse.service;

import be.kdg.sa.warehouse.domain.*;
import be.kdg.sa.warehouse.repository.PurchaseOrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class PurchaseOrderServiceTest {
    @Autowired
    private PurchaseOrderService purchaseOrderService;
    @MockBean
    private PurchaseOrderRepository purchaseOrderRepository;
    @MockBean
    private OrderLineService orderLineService;
    @MockBean
    private Clock clock;

    @Test
    public void calculateTodaysCommissionFromSellerShouldReturnPrice() {
        // Arrange
        var poUuid = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        var sellerPartyUuid = UUID.fromString("24fb6602-9b59-474c-a46c-820a1b50adab");
        var orderLine1 = new OrderLine(
                1,
                new Material(
                        1,
                        "Gips",
                        1,
                        13
                ),
                100);
        var orderLine2 = new OrderLine(
                2,
                new Material(
                        2,
                        "Ijzererts",
                        5,
                        110
                ),
                50);

        LocalDateTime fixedDateTime = LocalDateTime.of(2024, 10, 26, 9, 0, 0);
        Clock fixedClock = Clock.fixed(fixedDateTime.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());

        given(clock.instant()).willReturn(fixedClock.instant());
        given(clock.getZone()).willReturn(fixedClock.getZone());

        given(purchaseOrderRepository.findPurchaseOrdersBySellerPartyUuidAndFulfillDateAfter(sellerPartyUuid, LocalDateTime.of(2024, 10, 26, 9, 0, 0)))
                .willReturn(Optional.of(
                                List.of(
                                        new PurchaseOrder(
                                                poUuid,
                                                new Supplier(
                                                        sellerPartyUuid,
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
                                                List.of(
                                                        orderLine1,
                                                        orderLine2
                                                ),
                                                LocalDateTime.of(2024, 10, 25, 10, 0, 0)
                                        )
                                )
                        )
                );

        given(orderLineService.calculateLineCommission(orderLine1))
                .willReturn(1.0); // 100 * 1 * 0.01

        given(orderLineService.calculateLineCommission(orderLine2))
                .willReturn(55.0); // 50 * 110 * 0.01

        // Act
        var commission = purchaseOrderService.calculateTodaysCommission(sellerPartyUuid);

        // Assert
        assertEquals(56, commission);

        verify(purchaseOrderRepository, times(1)).findPurchaseOrdersBySellerPartyUuidAndFulfillDateAfter(any(UUID.class), any(LocalDateTime.class));
        verify(orderLineService, times(2)).calculateLineCommission(any(OrderLine.class));
    }

    @Test
    public void calculateTodaysCommissionFromSellerWithNoRecentSellsShouldReturnZero() {
        // Arrange
        var sellerPartyUuid = UUID.fromString("24fb6602-9b59-474c-a46c-820a1b50adab");

        LocalDateTime fixedDateTime = LocalDateTime.of(2024, 10, 26, 9, 0, 0);
        Clock fixedClock = Clock.fixed(fixedDateTime.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());

        given(clock.instant()).willReturn(fixedClock.instant());
        given(clock.getZone()).willReturn(fixedClock.getZone());

        given(purchaseOrderRepository.findPurchaseOrdersBySellerPartyUuidAndFulfillDateAfter(sellerPartyUuid, LocalDateTime.of(2024, 10, 26, 9, 0, 0)))
                .willReturn(Optional.of(List.of()));

        // Act
        var commission = purchaseOrderService.calculateTodaysCommission(sellerPartyUuid);

        // Assert
        assertEquals(0, commission);

        verify(purchaseOrderRepository, times(1)).findPurchaseOrdersBySellerPartyUuidAndFulfillDateAfter(any(UUID.class), any(LocalDateTime.class));
        verify(orderLineService, times(0)).calculateLineCommission(any(OrderLine.class));
    }
}