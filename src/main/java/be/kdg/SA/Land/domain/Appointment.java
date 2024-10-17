package be.kdg.SA.Land.domain;

import jakarta.persistence.*;

import java.util.UUID;
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
   private UUID appointmentId;
    @OneToOne
    private Supplier supplier;
    @OneToOne
   private Truck truck;
    @OneToOne
   private Resource resource;
    @OneToOne
   private ArrivalWindow arrivalWindow;

    protected Appointment() {} //voor jpa
    public Appointment(Supplier supplier, Truck truck, Resource resource, ArrivalWindow arrivalWindow) {
        this.supplier = supplier;
        this.truck = truck;
        this.resource = resource;
        this.arrivalWindow = arrivalWindow;
    }

    public UUID getAppointmentId() {
        return appointmentId;
    }

    public Truck getTruck() {
        return truck;
    }

    public Resource getRescource() {
        return resource;
    }

    public ArrivalWindow getArrivalWindow() {
        return arrivalWindow;
    }

    public Supplier getSupplier() {
        return supplier;
    }
}
