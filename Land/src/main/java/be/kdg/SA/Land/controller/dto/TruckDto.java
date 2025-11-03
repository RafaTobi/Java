package be.kdg.sa.land.controller.dto;

public class TruckDto {
    private String licensePlate;
    private Long weighBridgeNumber;
    private String message;

    // Constructors
    public TruckDto(String licensePlate, Long weighBridgeNumber, String message) {
        this.licensePlate = licensePlate;
        this.weighBridgeNumber = weighBridgeNumber;
        this.message = message;
    }


    public String getLicensePlate() {
        return licensePlate;
    }

    public Long getWeighBridgeNumber() {
        return weighBridgeNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setWeighBridgeNumber(Long weighBridgeNumber) {
        this.weighBridgeNumber = weighBridgeNumber;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

