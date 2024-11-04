package be.kdg.sa.land.controller;

import be.kdg.sa.land.controller.dto.AppointmentDto;
import be.kdg.sa.land.domain.ArrivalWindow;
import be.kdg.sa.land.domain.Resource;
import be.kdg.sa.land.domain.Supplier;
import be.kdg.sa.land.domain.Truck;
import be.kdg.sa.land.service.*;
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
public String addAppointment(@ModelAttribute("Appointment") @Valid AppointmentDto appointmentDto, BindingResult errors, Model model) {
    if (errors.hasErrors()) {
        model.addAttribute("TimeSlots", arrivalWindowService.getAvailableTimeSlots());
        return "AppointmentForm";
    }

    Supplier supplier;
    Truck truck;
    try {
        supplier = supplierService.findSupplier(appointmentDto.getSupplier());
        truck = truckService.findTruck(appointmentDto.getTruck());
    } catch (IllegalArgumentException e) {
        errors.rejectValue("supplier.name", "error.supplier", "Supplier does not exist in the database");
        errors.rejectValue("truck.licenseplate", "error.truck", "Truck does not exist in the database");
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

    appointmentDto.setTruck(truck);

    appointmentService.createAppointment(appointmentDto);

    return "redirect:/appointments";
}




}