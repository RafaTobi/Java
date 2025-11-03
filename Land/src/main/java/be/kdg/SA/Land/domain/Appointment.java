// Appointment.java
package be.kdg.sa.land.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID appointmentId;

    @ManyToOne
    private Supplier supplier;

    @ManyToOne
    private Truck truck;

    @ManyToOne
    private Resource resource;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "arrival_window_id")
    private ArrivalWindow arrivalWindow;



    public Appointment(Supplier supplier, Truck truck, Resource resource, ArrivalWindow arrivalWindow) {
        this.appointmentId = UUID.randomUUID();
        this.supplier = supplier;
        this.truck = truck;
        this.resource = resource;
        this.arrivalWindow = arrivalWindow;

    }
    protected Appointment() {} //voor jpa

    // Getters and setters
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