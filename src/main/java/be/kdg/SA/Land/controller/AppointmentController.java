package be.kdg.SA.Land.controller;

import be.kdg.SA.Land.controller.Dto.AppointmentDto;
import be.kdg.SA.Land.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppointmentController {


    private AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/appointments")
    public ModelAndView getAppointments() {
        ModelAndView modelAndView = new ModelAndView("Appointments");
        modelAndView.addObject("appointments", appointmentService.findAllAppointments());
        return modelAndView;
    }

    @GetMapping("/add-appointment")
    public String addAppointment(Model model) {
        model.addAttribute("Appointment", new AppointmentDto());
        return "AppointmentForm";
    }


    @PostMapping("/add-appointment")
    public String addAppointment(@ModelAttribute("Appointment") @Valid AppointmentDto Appointment, BindingResult errors){
        if(errors.hasErrors()){
            return "AppointmentForm";
        }
        appointmentService.createAppointment(Appointment.toSource());
        return "redirect:/appointments/" + Appointment.getAppointmentId();
    }






}
