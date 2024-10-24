package be.kdg.SA.Land.controller;

import be.kdg.SA.Land.service.FifoQueueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FifoQueueController {
    private final FifoQueueService fifoQueueService;

    public FifoQueueController(FifoQueueService fifoQueueService) {
        this.fifoQueueService = fifoQueueService;
    }

    @GetMapping("/fifo-queue")
    public String viewFifoQueue(Model model) {
        model.addAttribute("fifoQueue", fifoQueueService.getFifoQueue().getQueue());
        return "fifo-queue";
    }
}