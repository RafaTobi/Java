package be.kdg.sa.warehouse.controller.api;

import be.kdg.sa.warehouse.controller.dto.FullfilPoMessageDto;
import be.kdg.sa.warehouse.controller.dto.PurchaseOrderDto;
import be.kdg.sa.warehouse.domain.PurchaseOrder;
import be.kdg.sa.warehouse.service.PurchaseOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/purchaseorders")
public class PurchaseOrderRestController {
    private final PurchaseOrderService purchaseOrderService;
    private static final Logger logger = Logger.getLogger(PurchaseOrderRestController.class.getName());

    public PurchaseOrderRestController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @PostMapping()
    public ResponseEntity<PurchaseOrder> createPurchaseOrder(@Valid @RequestBody PurchaseOrderDto purchaseOrderDto) {
        logger.info("Creation of new purchase order started!");
        PurchaseOrder purchaseOrder = purchaseOrderService.createPurchaseOrder(purchaseOrderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseOrder);
    }

    @PostMapping("/fulfill") //wordt opgeroepen via water
    public void fulfillPurchaseOrder(@RequestBody FullfilPoMessageDto message) {
        logger.info("External call received!");
        logger.info("Fulfilling purchase order started!");
        purchaseOrderService.fulfillOrder(message);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getPurchaseOrder(@PathVariable UUID uuid) {
        Optional<PurchaseOrder> optPurchaseOrder = purchaseOrderService.getPurchaseOrdersByReferenceUuid(uuid);
        if (optPurchaseOrder.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Purchase Order Not Found");
        PurchaseOrderDto purchaseOrderDto = purchaseOrderService.mapToDto(optPurchaseOrder.get());
        return ResponseEntity.ok(purchaseOrderDto);
    }

    @GetMapping("/{orderStatus}/orderstatus")
    public ResponseEntity<List<PurchaseOrderDto>> getPurchaseOrdersByOrderStatus(@PathVariable boolean orderStatus) {
        List<PurchaseOrderDto> purchaseOrderDtos = new ArrayList<>();
        List<PurchaseOrder> purchaseOrders = purchaseOrderService.getPurchaseOrdersByOrderStatus(orderStatus);

        for (PurchaseOrder purchaseOrder : purchaseOrders) {
            purchaseOrderDtos.add(purchaseOrderService.mapToDto(purchaseOrder));
        }

        return ResponseEntity.ok(purchaseOrderDtos);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Purchase Order");
    }
}
