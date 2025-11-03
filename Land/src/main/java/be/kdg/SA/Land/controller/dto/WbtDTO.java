package be.kdg.sa.land.controller.dto;

import java.time.LocalDateTime;

public class WbtDTO {
    private long wbtId;
    private long weighBridgeNumber;
    private double arrivalWeight;
    private double departureWeight;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private String licencePlate;
}
