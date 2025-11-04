package be.kdg.sa.warehouse.handler;

import be.kdg.sa.warehouse.config.RabbitTopology;
import be.kdg.sa.warehouse.controller.dto.PdtMessageDto;
import be.kdg.sa.warehouse.service.PayloadDeliveryTicketService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PayloadDeliveryTicketMessageHandler {
    private final PayloadDeliveryTicketService pdtService;

    public PayloadDeliveryTicketMessageHandler(PayloadDeliveryTicketService pdtService) {
        this.pdtService = pdtService;
    }

    @RabbitListener(queues = RabbitTopology.TOPIC_QUEUE_PDT)
    public void processPdtMessage(PdtMessageDto pdtMessageDto) {
        pdtService.processPdtMessage(pdtMessageDto);
    }
}
