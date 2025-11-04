// AppointmentDto.java
package be.kdg.sa.land.controller.dto;

import be.kdg.sa.land.domain.ArrivalWindow;
import be.kdg.sa.land.domain.Resource;
import be.kdg.sa.land.domain.Supplier;
import be.kdg.sa.land.domain.Truck;
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