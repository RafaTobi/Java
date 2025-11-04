package be.kdg.sa.warehouse.service;

import be.kdg.sa.warehouse.controller.dto.OrderLineDto;
import be.kdg.sa.warehouse.domain.OrderLine;
import be.kdg.sa.warehouse.repository.OrderLineRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderLineService {
    private final OrderLineRepository orderLineRepository;

    public OrderLineService(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }

    public OrderLine getOrderLineById(long id) {
        Optional<OrderLine> orderline = orderLineRepository.findById(id);
        return orderline.orElse(null);
    }

    public OrderLine createOrderLine(OrderLine orderLine) {
        return orderLineRepository.save(orderLine);
    }

    public double calculateLineCommission(OrderLine orderLine) {
        double materialSellingPrice; //prijs/ton
        double lineQuantity;
        final double commissionPercentage = 0.01;

        lineQuantity = orderLine.getQuantity();
        materialSellingPrice = orderLine.getMaterial().getSellingPrice();

        return (lineQuantity * materialSellingPrice) * commissionPercentage;
    }

    public OrderLineDto mapToDto(OrderLine orderLine) {
        OrderLineDto orderLineDto = new OrderLineDto();

        orderLineDto.setLineNumber(orderLineDto.getLineNumber());
        orderLineDto.setQuantity(orderLineDto.getQuantity());
        orderLineDto.setMaterialType(orderLine.getMaterial().getName());
        orderLineDto.setUom(orderLine.getUom());

        return orderLineDto;
    }
}
