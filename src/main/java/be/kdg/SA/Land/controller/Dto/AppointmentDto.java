// AppointmentDto.java
package be.kdg.SA.Land.controller.Dto;

import be.kdg.SA.Land.domain.ArrivalWindow;
import be.kdg.SA.Land.domain.Resource;
import be.kdg.SA.Land.domain.Supplier;
import be.kdg.SA.Land.domain.Truck;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class AppointmentDto {
    private UUID appointmentId;
    @NotNull
    private Supplier supplier;
    @NotNull
    private Truck truck;
    @NotNull
    private Resource resource;
    @NotNull
    private ArrivalWindow arrivalWindow;

    // Getters and setters
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

    public UUID getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(UUID appointmentId) {
        this.appointmentId = appointmentId;
    }
}