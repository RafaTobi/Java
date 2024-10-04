package be.kdg.SA.Land.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppointmentController {

    @GetMapping("/appointments")
    public ModelAndView getAppointments() {
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }

}
