package be.kdg.sa.water.controller.dto;

import be.kdg.sa.water.domain.BunkerOperation;
import be.kdg.sa.water.domain.InspectionOperation;
import jakarta.persistence.OneToOne;

import java.time.LocalDateTime;
import java.util.UUID;

public class DockOperationDto {
    private long id;
    private UUID purchaseOrderReference;
    private String vesselNumber;
    private LocalDateTime arrival;
    private LocalDateTime departure;
    private long bunkerOperationId;
    private long inspectionOperationId;


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

    public String getVesselNumber() {
        return vesselNumber;
    }

    public void setVesselNumber(String vesselNumber) {
        this.vesselNumber = vesselNumber;
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

    public long getBunkerOperationId() {
        return bunkerOperationId;
    }

    public void setBunkerOperationId(long bunkerOperationId) {
        this.bunkerOperationId = bunkerOperationId;
    }

    public long getInspectionOperationId() {
        return inspectionOperationId;
    }

    public void setInspectionOperationId(long inspectionOperationId) {
        this.inspectionOperationId = inspectionOperationId;
    }
}
