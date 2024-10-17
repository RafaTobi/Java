package be.kdg.SA.Land.controller.Dto;

import be.kdg.SA.Land.domain.*;
import jakarta.validation.constraints.NotBlank;


import java.util.UUID;

public class AppointmentDto {
    private UUID appointmentId;
    @NotBlank(message = "supplier is mandatory")
    private Supplier supplier;
    @NotBlank(message = "truck is mandatory")
    private Truck truck;
    @NotBlank(message = "resource is mandatory")
    private Resource resource;
    @NotBlank(message = "arrivalWindow is mandatory")
    private ArrivalWindow arrivalWindow;

    public AppointmentDto(Appointment appointment) {
        this.appointmentId = appointment.getAppointmentId();
        this.supplier = appointment.getSupplier();
        this.truck = appointment.getTruck();
        this.resource = appointment.getResource();
        this.arrivalWindow = appointment.getArrivalWindow();

    }

    public AppointmentDto() {

    }
    public Appointment toSource() {
        return new Appointment(supplier, truck, resource, arrivalWindow);
    }


    public UUID getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(UUID appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public ArrivalWindow getArrivalWindow() {
        return arrivalWindow;
    }

    public void setArrivalWindow(ArrivalWindow arrivalWindow) {
        this.arrivalWindow = arrivalWindow;
    }
}
