// GateController.java
package be.kdg.SA.Land.controller;

import be.kdg.SA.Land.domain.Truck;
import be.kdg.SA.Land.service.FifoQueueService;
import be.kdg.SA.Land.service.TruckService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class GateController {

    private final TruckService truckService;
    private final FifoQueueService fifoQueueService;

    public GateController(TruckService truckService, FifoQueueService fifoQueueService) {
        this.truckService = truckService;
        this.fifoQueueService = fifoQueueService;
    }

    @GetMapping("/truck-entry")
    public String showTruckEntryForm() {
        return "truck-entry";
    }

    @PostMapping("/truck-entry")
    public String checkTruckEntry(@RequestParam("licensePlate") String licensePlate, Model model) {
        Optional<Truck> truckOpt = truckService.findTruckByLicenseplate(licensePlate);

        if (truckOpt.isPresent()) {
            Truck truck = truckOpt.get();
            String resultMessage = truckService.checkTruckEntry(licensePlate);

            if (resultMessage.contains("No valid appointment found")) {
                fifoQueueService.addTruckToQueue(truck);
                resultMessage = "Truck added to FIFO queue.";
            }

            model.addAttribute("message", resultMessage);
        } else {
            model.addAttribute("message", "No truck found with license plate " + licensePlate);
        }

        return "truck-entry-result";
    }
}