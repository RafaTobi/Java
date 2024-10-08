package be.kdg.SA.Land.controller.api;

import be.kdg.SA.Land.domain.WeighBridgeTicket;
import be.kdg.SA.Land.service.WeighBridgeTicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/weighbridgetickets")
public class WbtRestController {
    private final WeighBridgeTicketService ticketService;

    public WbtRestController(WeighBridgeTicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/arrival/{licencePlate}")
    public ResponseEntity<WeighBridgeTicket> recordArrival(@PathVariable String licencePlate) {
        WeighBridgeTicket ticket = ticketService.arrivalWeighIn(licencePlate, 1); //TODO hoe weegbrug bepalen?

        return ResponseEntity.ok(ticket);
    }
}
