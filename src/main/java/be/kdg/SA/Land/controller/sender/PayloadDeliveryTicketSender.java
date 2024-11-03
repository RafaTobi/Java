package be.kdg.SA.Land.controller.sender;

import be.kdg.SA.Land.config.RabbitTopology;
import be.kdg.SA.Land.controller.dto.PdtMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayloadDeliveryTicketSender {
    private final RabbitTemplate rabbitTemplate;

    public PayloadDeliveryTicketSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/pdt/create")
    public void sendPayloadDeliveryTicketMessage(@RequestBody PdtMessageDto pdtMessageDto) throws JsonProcessingException {
        rabbitTemplate.convertAndSend(RabbitTopology.WBT_EXCHANGE, "wbt.create", pdtMessageDto);
    }
}
