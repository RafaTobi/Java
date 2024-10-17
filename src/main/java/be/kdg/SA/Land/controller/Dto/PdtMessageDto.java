package be.kdg.SA.Land.controller.Dto;

import java.time.LocalDateTime;

public class PdtMessageDto {
    private long id;
    private double amount;
    private LocalDateTime timeOfDelivery;
    private String materialType;

    public PdtMessageDto() {
    }

    public PdtMessageDto(long id, double amount, LocalDateTime timeOfDelivery, String materialType) {
        this.id = id;
        this.amount = amount;
        this.timeOfDelivery = timeOfDelivery;
        this.materialType = materialType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
