package be.kdg.SA.Land.controller;

import be.kdg.SA.Land.controller.Dto.AppointmentDto;
import be.kdg.SA.Land.domain.ArrivalWindow;
import be.kdg.SA.Land.domain.Resource;
import be.kdg.SA.Land.domain.Supplier;
import be.kdg.SA.Land.domain.Truck;
import be.kdg.SA.Land.domain.enums.ResourceType;
import be.kdg.SA.Land.service.*;
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

    private final AppointmentService appointmentService;
    private final ArrivalWindowService arrivalWindowService;
    private final SupplierService supplierService;
    private final TruckService truckService;
    private final ResourceService resourceService;

    public AppointmentController(AppointmentService appointmentService, ArrivalWindowService arrivalWindowService, SupplierService supplierService, TruckService truckService, ResourceService resourceService) {
        this.appointmentService = appointmentService;
        this.arrivalWindowService = arrivalWindowService;
        this.supplierService = supplierService;
        this.truckService = truckService;
        this.resourceService = resourceService;
    }

    @GetMapping("/appointments")
    public ModelAndView getAppointments() {
        ModelAndView modelAndView = new ModelAndView("Appointments");
        modelAndView.addObject("appointments", appointmentService.findAllAppointments());
        return modelAndView;
    }


    @GetMapping("/add-appointment")
    public ModelAndView addAppointment() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("Appointment", new AppointmentDto());
        mav.addObject("TimeSlots", arrivalWindowService.getAvailableTimeSlots());
        mav.setViewName("AppointmentForm");
        return mav;
    }

    @PostMapping("/add-appointment")
    public String addAppointment(@ModelAttribute("Appointment") @Valid AppointmentDto appointmentDto, BindingResult errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("TimeSlots", arrivalWindowService.getAvailableTimeSlots());
            return "AppointmentForm";
        }

        Supplier supplier;
        try {
            supplier = supplierService.findSupplier(appointmentDto.getSupplier());
        } catch (IllegalArgumentException e) {

            errors.rejectValue("supplier.name", "error.supplier", "Supplier does not exist in the database");
            model.addAttribute("TimeSlots", arrivalWindowService.getAvailableTimeSlots());
            return "AppointmentForm";
        }


        ArrivalWindow arrivalWindow = arrivalWindowService.createArrivalWindow(
                appointmentDto.getArrivalWindow().getDate(),
                appointmentDto.getArrivalWindow().getStartTime()
        );
        appointmentDto.setArrivalWindow(arrivalWindow);
        appointmentDto.setSupplier(supplier);

        Resource resource = resourceService.findResource(appointmentDto.getResource());
        appointmentDto.setResource(resource);

        Truck truck = truckService.findTruck(appointmentDto.getTruck());
        appointmentDto.setTruck(truck);

        appointmentService.createAppointment(appointmentDto);

        return "redirect:/appointments";
    }



}