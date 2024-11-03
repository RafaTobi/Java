// src/main/java/be/kdg/SA/Land/controller/TruckController.java
package be.kdg.SA.Land.controller;

import be.kdg.SA.Land.domain.Truck;
import be.kdg.SA.Land.service.FifoQueueService;
import be.kdg.SA.Land.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class TruckController {
    private final TruckService truckService;
    private final FifoQueueService fifoQueueService;

    @Autowired
    public TruckController(TruckService truckService, FifoQueueService fifoQueueService) {
        this.truckService = truckService;
        this.fifoQueueService = fifoQueueService;
    }

    @GetMapping("/truck-entry")
    public String showTruckEntryForm() {
        return "truck-entry";
    }

    @PostMapping("/truck-entry")
    public String checkTruckEntry(@RequestParam("licensePlate") String licensePlate, Model model) {
        Optional<Truck> truckOpt = truckService.checkTruckEntry(licensePlate);
        if (truckOpt.isPresent()) {
            model.addAttribute("truck", truckOpt.get());
            return "truck-entry-succes";
        } else {
            model.addAttribute("message", "Truck added to FIFO queue.");
            return "truck-entry-result";
        }
    }

    @GetMapping("/fifo-queue")
    public String getFifoQueue(Model model) {
        model.addAttribute("fifoQueue", fifoQueueService.getFifoQueue().getQueue());
        return "fifo-queue";
    }

    @GetMapping("/trucks-on-site")
    public String countTrucksOnSite(Model model) {
        long count = truckService.countTrucksOnSite();
        model.addAttribute("count", count);
        return "trucks-on-site";
    }
}