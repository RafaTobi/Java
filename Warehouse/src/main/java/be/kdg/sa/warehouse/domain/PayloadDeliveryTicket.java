package be.kdg.sa.warehouse.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PayloadDeliveryTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double amount;
    private LocalDateTime timeOfDelivery;
    private double storageCost;
    @ManyToOne
    private Material material;
    @ManyToOne
    private Warehouse warehouse;

    public PayloadDeliveryTicket(){}

    public PayloadDeliveryTicket(long id, double amount, LocalDateTime timeOfDelivery, double storageCost) {
        this.id = id;
        this.amount = amount;
        this.timeOfDelivery = timeOfDelivery;
        this.storageCost = storageCost;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getTimeOfDelivery() {
        return timeOfDelivery;
    }

    public void setTimeOfDelivery(LocalDateTime timeOfDelivery) {
        this.timeOfDelivery = timeOfDelivery;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getStorageCost() {
        return storageCost;
    }

    public void setStorageCost(double storageCost) {
        this.storageCost = storageCost;
    }
}
