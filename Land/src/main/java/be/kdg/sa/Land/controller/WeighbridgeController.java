package be.kdg.sa.land.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WeighbridgeController {

    @GetMapping("/weighbridge")
    public String showWeighbridgePage(Model model) {
        // Add any necessary attributes to the model
        return "weighbridgeticket";
    }
}