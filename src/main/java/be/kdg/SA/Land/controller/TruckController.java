// src/main/java/be/kdg/SA/Land/controller/TruckController.java
package be.kdg.sa.land.controller;

import be.kdg.sa.land.controller.dto.PdtMessageDto;
import be.kdg.sa.land.domain.Truck;
import be.kdg.sa.land.service.*;
import be.kdg.sa.land.controller.sender.PayloadDeliveryTicketSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class TruckController {
    private final TruckService truckService;
    private final WeighBridgeTicketService weighBridgeTicketService;
    private final AppointmentService appointmentService;
    private final FifoQueueService fifoQueueService;
    private final PayloadDeliveryTicketSender payloadDeliveryTicketSender;

    @Autowired
    public TruckController(TruckService truckService, WeighBridgeTicketService weighBridgeTicketService, FifoQueueService fifoQueueService, AppointmentService appointmentService, PayloadDeliveryTicketSender payloadDeliveryTicketSender) {
        this.truckService = truckService;
        this.weighBridgeTicketService = weighBridgeTicketService;
        this.fifoQueueService = fifoQueueService;
        this.appointmentService = appointmentService;
        this.payloadDeliveryTicketSender = payloadDeliveryTicketSender;
    }

    @GetMapping("/truck-entry")
    public String showTruckEntryForm() {
        return "truck-entry";
    }

    @PostMapping("/truck-entry")
    public String checkTruckEntry(@RequestParam("licensePlate") String licensePlate, @RequestParam("weight") double weight, Model model) {
        Optional<Truck> truckOpt = truckService.findTruckByLicenseplate(licensePlate);
        if (truckOpt.isPresent()) {
            Truck truck = truckOpt.get();
            if (appointmentService.isTruckScheduledForNow(truck)) {
                long weighBridgeNumber = weighBridgeTicketService.assignWeighBridgeNumber(licensePlate);
                appointmentService.logArrivalTime(licensePlate, LocalDateTime.now());

                // Here we use the actual weight from the form
                weighBridgeTicketService.logTruckWeight(weighBridgeNumber, licensePlate, weight, true);

                PdtMessageDto message = new PdtMessageDto(truck.getLicenseplate(), weighBridgeNumber);
                try {
                    payloadDeliveryTicketSender.sendPayloadDeliveryTicketMessage(message);
                } catch (JsonProcessingException e) {
                    model.addAttribute("message", "Error sending message to warehouse.");
                    return "truck-entry-result";
                }
                model.addAttribute("weighBridgeNumber", weighBridgeNumber);
                model.addAttribute("truck", truck);
                return "truck-entry-success";
            } else {
                fifoQueueService.addTruckToQueue(truck);
                model.addAttribute("message", "Truck added to FIFO queue.");
                return "truck-entry-result";
            }
        } else {
            model.addAttribute("message", "Truck not found.");
            return "truck-entry-result";
        }
    }

    @GetMapping("/truck-return")
    public String showTruckReturnForm(@RequestParam("licensePlate") String licensePlate, Model model) {
        Optional<Truck> truckOpt = truckService.findTruckByLicenseplate(licensePlate);
        if (truckOpt.isPresent()) {
            Truck truck = truckOpt.get();
            model.addAttribute("truck", truck);
            return "truck-return-form"; // Form for entering the departure weight
        } else {
            model.addAttribute("message", "Truck not found.");
            return "truck-return-failure";
        }
    }

    @PostMapping("/truck-return")
    public String handleTruckReturn(@RequestParam("licensePlate") String licensePlate, @RequestParam("departureWeight") double departureWeight, Model model) {
        Optional<Truck> truckOpt = truckService.findTruckByLicenseplate(licensePlate);
        if (truckOpt.isPresent()) {
            Truck truck = truckOpt.get();
            weighBridgeTicketService.logTruckDeparture(truck, departureWeight);
            model.addAttribute("truck", truck);
            model.addAttribute("ticket", truck.getWbTicket());
            return "truck-return-success";
        } else {
            model.addAttribute("message", "Truck not found.");
            return "truck-return-failure";
        }
    }

    @GetMapping("/weighbridgeticket")
    public String showWeighbridgeTicket(@RequestParam("licensePlate") String licensePlate, Model model) {
        Optional<Truck> truckOpt = truckService.findTruckByLicenseplate(licensePlate);
        if (truckOpt.isPresent()) {
            Truck truck = truckOpt.get();
            if (truck.getWbTicket() != null) {
                model.addAttribute("ticket", truck.getWbTicket());
                model.addAttribute("truck", truck);
                return "weighbridgeticket"; // The template that displays weighbridge ticket details
            } else {
                model.addAttribute("message", "No weighbridge ticket found for this truck.");
                return "truck-return-failure";
            }
        } else {
            model.addAttribute("message", "Truck not found.");
            return "truck-return-failure";
        }
    }

    @GetMapping("/trucks-on-site")
    public String countTrucksOnSite(Model model) {
        long count = truckService.countTrucksOnSite();
        model.addAttribute("count", count);
        return "trucks-on-site";
    }

    @GetMapping("/arrival-departure-log")
    public String showArrivalDepartureLog(Model model) {
        model.addAttribute("logs", truckService.getArrivalDepartureLogs());
        return "arrival-departure-log";
    }
}
