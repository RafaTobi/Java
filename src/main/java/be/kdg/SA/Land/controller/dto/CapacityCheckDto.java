package be.kdg.sa.land.controller.dto;

import java.util.UUID;

public class CapacityCheckDto {
    private UUID userUuid;
    private String materialType;

    // Constructor
    public CapacityCheckDto(UUID userUuid, String materialType) {
        this.userUuid = userUuid;
        this.materialType = materialType;
    }

    // Getters and Setters
    public UUID getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }
}
