package be.kdg.SA.Land.controller.api;

import be.kdg.SA.Land.config.RabbitTopology;
import be.kdg.SA.Land.controller.Dto.PdtMessageDto;
import be.kdg.SA.Land.controller.Dto.WbtDTO;
import be.kdg.SA.Land.domain.WeighBridgeTicket;
import be.kdg.SA.Land.service.WeighBridgeTicketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weighbridgetickets")
public class WbtRestController {
    private final WeighBridgeTicketService ticketService;
    private final RabbitTemplate rabbitTemplate;

    public WbtRestController(WeighBridgeTicketService ticketService, RabbitTemplate rabbitTemplate) {
        this.ticketService = ticketService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/arrival/{licencePlate}")
    public ResponseEntity<WeighBridgeTicket> recordArrival(@PathVariable String licencePlate) {
        WeighBridgeTicket ticket = ticketService.arrivalWeighIn(licencePlate, 1); //TODO hoe weegbrug bepalen?

        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/departure/{licencePlate}")
    public ResponseEntity<WeighBridgeTicket> recordDeparture(@PathVariable String licencePlate) {
        WeighBridgeTicket ticket = ticketService.departureWeighIn(licencePlate);

        return ResponseEntity.ok(ticket);
    }

    @PostMapping("/create")
    public void completeDelivery(@RequestBody PdtMessageDto pdtMessageDto) throws JsonProcessingException {
        rabbitTemplate.convertAndSend(RabbitTopology.WBT_EXCHANGE, "wbt.create", pdtMessageDto);
    }
}
