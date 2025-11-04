package be.kdg.sa.land.controller.dto;

import be.kdg.sa.land.domain.enums.ResourceType;

import java.time.LocalDateTime;
import java.util.UUID;

public class PdtMessageDto {
    private UUID supplierUuid;
    private double amount;
    private LocalDateTime timeOfDelivery;
    private String materialType;

    public PdtMessageDto(UUID supplierUuid, double amount, LocalDateTime timeOfDelivery, String materialType) {
        this.supplierUuid = supplierUuid;
        this.amount = amount;
        this.timeOfDelivery = timeOfDelivery;
        this.materialType = materialType;
    }

    public UUID getSupplierUuid() {
        return supplierUuid;
    }

    public void setSupplierUuid(UUID supplierUuid) {
        this.supplierUuid = supplierUuid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimeOfDelivery() {
        return timeOfDelivery;
    }

    public void setTimeOfDelivery(LocalDateTime timeOfDelivery) {
        this.timeOfDelivery = timeOfDelivery;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }
}
