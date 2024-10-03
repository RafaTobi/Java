package be.kdg.SA.Land.domain;

import jakarta.persistence.*;

import java.util.UUID;
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
   private UUID afspraakId;
    @OneToOne
    private Supplier supplier;
    @OneToOne
   private Truck truck;
    @OneToOne
   private Rescource rescource;
    @OneToOne
   private ArrivalWindow arrivalWindow;

    protected Appointment() {} //voor jpa
    public Appointment(Supplier supplier, Truck truck, Rescource rescource, ArrivalWindow arrivalWindow) {
        this.supplier = supplier;
        this.truck = truck;
        this.rescource = rescource;
        this.arrivalWindow = arrivalWindow;
    }

    public UUID getAfspraakId() {
        return afspraakId;
    }

    public Truck getVrachtwagen() {
        return truck;
    }

    public Rescource getGrondstof() {
        return rescource;
    }

    public ArrivalWindow getAankomstVenster() {
        return arrivalWindow;
    }
}
