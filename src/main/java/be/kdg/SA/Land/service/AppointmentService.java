// AppointmentService.java
package be.kdg.SA.Land.service;

import be.kdg.SA.Land.controller.Dto.AppointmentDto;
import be.kdg.SA.Land.domain.Appointment;
import be.kdg.SA.Land.domain.ArrivalWindow;
import be.kdg.SA.Land.domain.Resource;
import be.kdg.SA.Land.domain.Supplier;
import be.kdg.SA.Land.domain.Truck;
import be.kdg.SA.Land.repository.AppointmentRepository;
import be.kdg.SA.Land.repository.ArrivalWindowRepository;
import be.kdg.SA.Land.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final ArrivalWindowRepository arrivalWindowRepository;
    private final ResourceRepository resourceRepository;
    private final SupplierService supplierService;
    private final TruckService truckService;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, ArrivalWindowRepository arrivalWindowRepository, ResourceRepository resourceRepository, SupplierService supplierService, TruckService truckService) {
        this.appointmentRepository = appointmentRepository;
        this.arrivalWindowRepository = arrivalWindowRepository;
        this.resourceRepository = resourceRepository;
        this.supplierService = supplierService;
        this.truckService = truckService;
    }
    public List<Appointment> findAllAppointments() {
        return this.appointmentRepository.findAll();
    }


    @Transactional
    public void createAppointment(AppointmentDto appointmentDto) {
        Supplier supplier = supplierService.findSupplier(appointmentDto.getSupplier());
        Truck truck = truckService.findTruck(appointmentDto.getTruck());
        ArrivalWindow arrivalWindow = appointmentDto.getArrivalWindow();
        Resource resource = appointmentDto.getResource();

        if (arrivalWindow.getId() == null) {
            arrivalWindowRepository.save(arrivalWindow);
        }

        if (resource.getName() == null) {
            resourceRepository.save(resource);
        }

        Appointment appointment = new Appointment(supplier, truck, resource, arrivalWindow);
        appointmentRepository.save(appointment);
    }

    public boolean isTruckScheduledForNow(Truck truck) {
        // Fetch current time
        LocalDateTime currentTime = LocalDateTime.now();

        // Find an appointment that matches the truck and its time window
        return appointmentRepository.existsByTruckAndArrivalWindowTime(truck, currentTime);
    }
}