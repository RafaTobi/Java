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
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final ArrivalWindowRepository arrivalWindowRepository;
    private final ResourceRepository resourceRepository;
    private final SupplierService supplierService;
    private final TruckService truckService;
    private final WeighBridgeTicketService weighBridgeTicketService;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, ArrivalWindowRepository arrivalWindowRepository, ResourceRepository resourceRepository, SupplierService supplierService, TruckService truckService, WeighBridgeTicketService weighBridgeTicketService) {
        this.appointmentRepository = appointmentRepository;
        this.arrivalWindowRepository = arrivalWindowRepository;
        this.resourceRepository = resourceRepository;
        this.supplierService = supplierService;
        this.truckService = truckService;
        this.weighBridgeTicketService = weighBridgeTicketService;
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
        LocalDateTime currentTime = LocalDateTime.now();
        return appointmentRepository.existsByTruckAndArrivalWindowTime(truck, currentTime);
    }

    public void logArrivalTime(String licensePlate, LocalDateTime actualArrivalTime) {
        Optional<Truck> truckOpt = truckService.findTruckByLicenseplate(licensePlate);
        if (truckOpt.isEmpty()) {
            return;
        }
        Truck truck = truckOpt.get();
        Optional<List<Appointment>> appointmentOpt = appointmentRepository.findAppointmentByTruck(truck);
        if (appointmentOpt.isPresent() && !appointmentOpt.get().isEmpty()) {
            Appointment appointment = appointmentOpt.get().get(0);
            ArrivalWindow arrivalWindow = appointment.getArrivalWindow();
            arrivalWindow.setStartTime(actualArrivalTime.toLocalTime());
            arrivalWindowRepository.save(arrivalWindow);
        }
    }

    public void logDepartureTime(String licensePlate, LocalDateTime departureTime) {
        Optional<Truck> truckOpt = truckService.findTruckByLicenseplate(licensePlate);
        if (truckOpt.isEmpty()) {
            return;
        }
        Truck truck = truckOpt.get();
        Optional<List<Appointment>> appointmentOpt = appointmentRepository.findAppointmentByTruck(truck);
        if (appointmentOpt.isPresent() && !appointmentOpt.get().isEmpty()) {
            Appointment appointment = appointmentOpt.get().get(0);
            ArrivalWindow arrivalWindow = appointment.getArrivalWindow();
            arrivalWindow.setEndTime(departureTime.toLocalTime());
            arrivalWindowRepository.save(arrivalWindow);
        }
    }

    public boolean canScheduleTruck(LocalDateTime arrivalTime) {
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(20, 0);
        LocalTime arrivalLocalTime = arrivalTime.toLocalTime();

        if (arrivalLocalTime.isBefore(startTime) || arrivalLocalTime.isAfter(endTime)) {
            return false;
        }

        long trucksScheduled = appointmentRepository.countAppointmentsWithinDateAndTime(
                arrivalTime.toLocalDate(),
                arrivalLocalTime.withMinute(0).withSecond(0).withNano(0),
                arrivalLocalTime.withMinute(59).withSecond(59).withNano(999999999)
        );

        return trucksScheduled < 40;
    }


}