package be.kdg.sa.water.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class DockOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private UUID purchaseOrderReference;
    private LocalDateTime arrival;
    private LocalDateTime departure;
    @OneToOne
    private BunkerOperation bunkerOperation;
    @OneToOne
    private InspectionOperation inspectionOperation;
    private String vesselNumber;

    public DockOperation(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getPurchaseOrderReference() {
        return purchaseOrderReference;
    }

    public void setPurchaseOrderReference(UUID purchaseOrderReference) {
        this.purchaseOrderReference = purchaseOrderReference;
    }

    public LocalDateTime getArrival() {
        return arrival;
    }

    public void setArrival(LocalDateTime arrival) {
        this.arrival = arrival;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDateTime departure) {
        this.departure = departure;
    }

    public BunkerOperation getBunkerOperation() {
        return bunkerOperation;
    }

    public void setBunkerOperation(BunkerOperation bunkerOperation) {
        this.bunkerOperation = bunkerOperation;
    }

    public InspectionOperation getInspectionOperation() {
        return inspectionOperation;
    }

    public void setInspectionOperation(InspectionOperation inspectionOperation) {
        this.inspectionOperation = inspectionOperation;
    }

    public String getVesselNumber() {
        return vesselNumber;
    }

    public void setBoat(String boatNumber) {
        this.vesselNumber = boatNumber;
    }
}
