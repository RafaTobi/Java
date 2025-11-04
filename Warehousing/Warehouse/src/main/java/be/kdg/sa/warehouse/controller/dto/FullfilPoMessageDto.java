package be.kdg.sa.warehouse.controller.dto;

import java.util.UUID;

public class FullfilPoMessageDto {
    private UUID poReference;
    private String vesselNumber;


    public UUID getPoReference() {
        return poReference;
    }

    public void setPoReference(UUID poReference) {
        this.poReference = poReference;
    }

    public String getVesselNumber() {
        return vesselNumber;
    }

    public void setVesselNumber(String vesselNumber) {
        this.vesselNumber = vesselNumber;
    }
}
