package be.kdg.SA.Land.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
public class WeighBridgeTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long wbtId;

    private long weighBridgeNumber;
    private double arrivalWeight;
    private double departureWeight;
    private double netWeight;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    @OneToOne
    private Truck truck;


    public WeighBridgeTicket() {
    }

    public long getWbtId() {
        return wbtId;
    }

    public void setWbtId(long wbtId) {
        this.wbtId = wbtId;
    }

    public long getWeighBridgeNumber() {
        return weighBridgeNumber;
    }

    public void setWeighBridgeNumber(long weighBridgeNumber) {
        this.weighBridgeNumber = weighBridgeNumber;
    }

    public double getArrivalWeight() {
        return arrivalWeight;
    }

    public void setArrivalWeight(double arrivalWeight) {
        this.arrivalWeight = arrivalWeight;
    }

    public double getDepartureWeight() {
        return departureWeight;
    }

    public void setDepartureWeight(double departureWeight) {
        this.departureWeight = departureWeight;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(double netWeight) {
        this.netWeight = netWeight;
    }
}
