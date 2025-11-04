package be.kdg.sa.warehouse.service;

import be.kdg.sa.warehouse.controller.api.MaterialRestController;
import be.kdg.sa.warehouse.controller.dto.*;
import be.kdg.sa.warehouse.domain.*;
import be.kdg.sa.warehouse.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaserService purchaserService;
    private final SupplierService supplierService;
    private final OrderLineService orderLineService;
    private final MaterialService materialService;
    private final WarehouseService warehouseService;
    private final PayloadDeliveryTicketService payloadDeliveryTicketService;
    private static final Logger logger = Logger.getLogger(PurchaseOrderService.class.getName());
    private final Clock clock;


    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, PurchaserService purchaserService, SupplierService supplierService, OrderLineService orderLineService, MaterialService materialService, WarehouseService warehouseService, PayloadDeliveryTicketService payloadDeliveryTicketService, Clock clock) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaserService = purchaserService;
        this.supplierService = supplierService;
        this.orderLineService = orderLineService;
        this.materialService = materialService;
        this.warehouseService = warehouseService;
        this.payloadDeliveryTicketService = payloadDeliveryTicketService;
        this.clock = clock;
    }

    public PurchaseOrder createPurchaseOrder(PurchaseOrderDto poDto) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setPoNumber(poDto.getPoNumber());
        purchaseOrder.setReferenceUuid(poDto.getReferenceUuid());
        purchaseOrder.setVesselNumber(poDto.getVesselNumber());

        // COSTUMERS EN SELLER KUNNEN WORDEN AANGEMAAKT MAAR UUID IS ALTIJD RANDOM GENERATED DUS BLIJVEN NIET HETZELFDE ALS REQUEST
        Optional<Purchaser> optCostumer = purchaserService.getPurchaserById(poDto.getCustomerParty().getUuid());
        if (optCostumer.isEmpty()) {
            Purchaser costumer = purchaserService.mapToPurchaser(poDto.getCustomerParty());
            costumer = purchaserService.createPurchaser(costumer);
            purchaseOrder.setCostumerParty(costumer);
        } else {
            purchaseOrder.setCostumerParty(optCostumer.get());
        }

        Optional<Supplier> optSeller = supplierService.getSupplierById(poDto.getSellerParty().getUuid());
        if (optSeller.isEmpty()) {
            Supplier seller = supplierService.mapToSupplier(poDto.getSellerParty());
            seller = supplierService.createSupplier(seller);
            purchaseOrder.setSellerParty(seller);
        } else {
            purchaseOrder.setSellerParty(optSeller.get());
        }


        List<OrderLine> orderLines = new ArrayList<>();
        for (OrderLineDto orderLineDto : poDto.getOrderLines()) {
            OrderLine orderLine = orderLineService.getOrderLineById(orderLineDto.getLineNumber());
            if (orderLine == null) {
                orderLine = new OrderLine();
                orderLine.setLineNumber(orderLineDto.getLineNumber());
                orderLine.setQuantity(orderLineDto.getQuantity());

                Material material = materialService.getMaterialByName(orderLineDto.getMaterialType());
                orderLine.setMaterial(material);
                orderLineService.createOrderLine(orderLine);
            }
            orderLines.add(orderLine);
        }
        purchaseOrder.setOrderLines(orderLines);

        return purchaseOrderRepository.save(purchaseOrder);
    }

    public PurchaseOrder updatePurchaseOrderOrderStatus(PurchaseOrder purchaseOrder, boolean newOrderFulfilled) {
        if (newOrderFulfilled && !purchaseOrder.isOrderFulfilled()) {
            purchaseOrder.setFulfillDate(LocalDateTime.now());
        }
        purchaseOrder.setOrderFulfilled(newOrderFulfilled);
        return purchaseOrderRepository.save(purchaseOrder);
    }

    public List<PurchaseOrder> getPurchaseOrdersByOrderStatus(boolean orderStatus) {
        Optional<List<PurchaseOrder>> purchaseOrders = purchaseOrderRepository.findPurchaseOrdersByOrderFulfilled(orderStatus);
        return purchaseOrders.orElse(null);
    }

    public void fulfillOrder(FullfilPoMessageDto message) { // Ik ga er momenteel vanuit dat het altijd succesvol is
        Optional<PurchaseOrder> optPurchaseOrder = purchaseOrderRepository.findPurchaseOrderByReferenceUuidAndVesselNumberWithOrderLines(message.getPoReference(), message.getVesselNumber());
        if (optPurchaseOrder.isEmpty()) {
            logger.warning("No existing purchase order found!");
            return;
        }
        PurchaseOrder purchaseOrder = optPurchaseOrder.get();

        Supplier sellerParty = purchaseOrder.getSellerParty();
        Warehouse warehouse;
        Material lineMaterial;
        for (OrderLine orderLine : purchaseOrder.getOrderLines()) {
            lineMaterial = orderLine.getMaterial();
            warehouse = warehouseService.findAllWarehousesFromSupplierStoragingMaterial(sellerParty.getUuid(), lineMaterial.getId());
            payloadDeliveryTicketService.fulfillOrderLine(orderLine.getQuantity(), warehouse.getId());
        }

        logger.info("The purchase order's order lines have successfully been handled!");
        updatePurchaseOrderOrderStatus(purchaseOrder, true);
    }

    public Optional<PurchaseOrder> getPurchaseOrdersByReferenceUuid(UUID uuid) {
        return purchaseOrderRepository.findPurchaseOrderByReferenceUuidWithOrderLines(uuid);
    }

    public double calculateTodaysCommission(UUID sellerPartyUuid) {
        double commissionPrice = 0;
        LocalDateTime nineOclock = nineOclock();
        Optional<List<PurchaseOrder>> optPOs = purchaseOrderRepository.findPurchaseOrdersBySellerPartyUuidAndFulfillDateAfter(sellerPartyUuid, nineOclock);

        if (optPOs.isEmpty()) return 0;
        List<PurchaseOrder> purchaseOrders = optPOs.get();
        for (PurchaseOrder purchaseOrder : purchaseOrders) {
            for (OrderLine orderLine : purchaseOrder.getOrderLines()) {
                commissionPrice += orderLineService.calculateLineCommission(orderLine);
            }
        }

        return commissionPrice;
    }

    public LocalDateTime nineOclock() {
        LocalDateTime now = LocalDateTime.now(clock);
        LocalDate today = LocalDate.now(clock);

        LocalDateTime nineOclock = today.atTime(9, 0, 0);
        if (now.isBefore(nineOclock)) {
            nineOclock = nineOclock.minusDays(1);
        }

        return nineOclock;
    }

    public PurchaseOrderDto mapToDto(PurchaseOrder purchaseOrder) {
        SupplierDto sellerPartyDto = supplierService.mapToDto(purchaseOrder.getSellerParty());
        PurchaserDto costumerPartyDto = purchaserService.mapToDto(purchaseOrder.getCostumerParty());

        List<OrderLineDto> orderLineDtos = new ArrayList<>();
        for (OrderLine orderLine : purchaseOrder.getOrderLines()) {
            orderLineDtos.add(orderLineService.mapToDto(orderLine));
        }

        PurchaseOrderDto purchaseOrderDto = new PurchaseOrderDto();

        purchaseOrderDto.setPoNumber(purchaseOrder.getPoNumber());
        purchaseOrderDto.setReferenceUuid(purchaseOrder.getReferenceUuid());
        purchaseOrderDto.setVesselNumber(purchaseOrder.getVesselNumber());
        purchaseOrderDto.setOrderFulfilled(purchaseOrder.isOrderFulfilled());
        purchaseOrderDto.setFulfillDate(purchaseOrder.getFulfillDate());
        purchaseOrderDto.setSellerParty(sellerPartyDto);
        purchaseOrderDto.setCustomerParty(costumerPartyDto);
        purchaseOrderDto.setOrderLines(orderLineDtos);

        return purchaseOrderDto;
    }
}
