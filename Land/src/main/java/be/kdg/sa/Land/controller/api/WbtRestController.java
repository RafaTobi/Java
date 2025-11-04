package be.kdg.sa.land.controller.api;

import be.kdg.sa.land.domain.WeighBridgeTicket;
import be.kdg.sa.land.service.WeighBridgeTicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weighbridgetickets")
public class WbtRestController {
    private final WeighBridgeTicketService ticketService;

    public WbtRestController(WeighBridgeTicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/arrival/{licencePlate}")
public ResponseEntity<WeighBridgeTicket> recordArrival(@PathVariable String licencePlate) {
    long weighBridgeNumber = ticketService.assignWeighBridgeNumber(licencePlate);
    ticketService.logTruckWeight(weighBridgeNumber, licencePlate, 0.0, true); // Assuming weight is 0.0 for now
    return ResponseEntity.ok().build();
}

@GetMapping("/departure/{licencePlate}")
public ResponseEntity<WeighBridgeTicket> recordDeparture(@PathVariable String licencePlate) {
    long weighBridgeNumber = ticketService.assignWeighBridgeNumber(licencePlate);
    ticketService.logTruckWeight(weighBridgeNumber, licencePlate, 0.0, false); // Assuming weight is 0.0 for now
    return ResponseEntity.ok().build();
}
}
