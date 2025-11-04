package be.kdg.sa.warehouse.service;

import be.kdg.sa.warehouse.controller.api.MaterialRestController;
import be.kdg.sa.warehouse.controller.dto.PdtMessageDto;
import be.kdg.sa.warehouse.domain.Material;
import be.kdg.sa.warehouse.domain.PayloadDeliveryTicket;
import be.kdg.sa.warehouse.controller.dto.PayloadDeliveryTicketDto;
import be.kdg.sa.warehouse.domain.Warehouse;
import be.kdg.sa.warehouse.repository.PayloadDeliveryTicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class PayloadDeliveryTicketService {
    private final PayloadDeliveryTicketRepository pdtRepository;
    private final WarehouseService warehouseService;
    private final MaterialService materialService;
    private static final Logger logger = Logger.getLogger(PayloadDeliveryTicketService.class.getName());


    public PayloadDeliveryTicketService(PayloadDeliveryTicketRepository pdtRepository, WarehouseService warehouseService, MaterialService materialService) {
        this.pdtRepository = pdtRepository;
        this.warehouseService = warehouseService;
        this.materialService = materialService;
    }

    public void fulfillOrderLine(double quantity, long warehouseId) {
        Optional<List<PayloadDeliveryTicket>> optPdts = pdtRepository.findPayloadDeliveryTicketsByWarehouseIdOrderByTimeOfDeliveryAsc(warehouseId);

        if (optPdts.isPresent() && !optPdts.get().isEmpty()) {
            List<PayloadDeliveryTicket> pdts = optPdts.get();

            for (PayloadDeliveryTicket pdt : pdts) {
                quantity = calculateLeftoverQuantity(pdt, quantity, warehouseId);

                if (quantity <= 0) {
                    break;
                }
            }
        }
    }

    public double calculateLeftoverQuantity(PayloadDeliveryTicket pdt, double quantity, long warehouseId) {
        double pdtAmountRemaining = pdt.getAmount();

        if (quantity > pdtAmountRemaining) {
            warehouseService.retrieveMaterialsFromWarehouse(warehouseId, pdtAmountRemaining);
            quantity -= pdtAmountRemaining;

            pdt.setAmount(0);
            pdtRepository.save(pdt);
        } else if (pdtAmountRemaining > quantity) {
            warehouseService.retrieveMaterialsFromWarehouse(warehouseId, quantity);
            pdtAmountRemaining -= quantity;
            quantity = 0;

            pdt.setAmount(pdtAmountRemaining);
            pdtRepository.save(pdt);
        }
        return quantity;
    }

    public List<PayloadDeliveryTicket> getPayloadDeliveryTicketsByWarehouseId(long warehouseId) {
        Optional<List<PayloadDeliveryTicket>> optPdts = pdtRepository.findPayloadDeliveryTicketsByWarehouseIdAAndAmountGreaterThanZeroOrderByTimeOfDeliveryAsc(warehouseId);
        if (optPdts.isPresent() && !optPdts.get().isEmpty()) {
            List<PayloadDeliveryTicket> pdts = optPdts.get();
            return pdts;
        } else {
            return null;
        }
    }

    public void processPdtMessage(PdtMessageDto pdtMessageDto) {
        Optional<Warehouse> optWarehouse = warehouseService.findWarehouseFromSupplierByMaterialType(pdtMessageDto.getSupplierUuid(), pdtMessageDto.getMaterialType());
        Material material = materialService.getMaterialByName(pdtMessageDto.getMaterialType());
        Warehouse warehouse;
        if (optWarehouse.isEmpty()) {
            logger.warning("Warehouse not found. Creating new one!");
            warehouse = warehouseService.createWarehouse(pdtMessageDto.getSupplierUuid(), pdtMessageDto.getMaterialType());
        } else warehouse = optWarehouse.get();

        warehouseService.addMaterialsToWarehouse(warehouse.getId(), pdtMessageDto.getAmount());

        PayloadDeliveryTicket pdt = new PayloadDeliveryTicket();
        pdt.setAmount(pdtMessageDto.getAmount());
        pdt.setTimeOfDelivery(pdtMessageDto.getTimeOfDelivery());
        pdt.setStorageCost(material.getStorageCost());
        pdt.setMaterial(material);
        pdt.setWarehouse(warehouse);
        pdtRepository.save(pdt);
    }
}
