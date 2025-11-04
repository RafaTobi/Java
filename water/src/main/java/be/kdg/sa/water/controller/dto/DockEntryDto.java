package be.kdg.sa.water.controller.dto;

import java.util.UUID;

public class DockEntryDto {
    private String vesselNumber;
    private UUID poReference;
    private UUID supplierUuid;


    public String getVesselNumber() {
        return vesselNumber;
    }

    public void setVesselNumber(String vesselNumber) {
        this.vesselNumber = vesselNumber;
    }

    public UUID getPoReference() {
        return poReference;
    }

    public void setPoReference(UUID poReference) {
        this.poReference = poReference;
    }

    public UUID getSupplierUuid() {
        return supplierUuid;
    }

    public void setSupplierUuid(UUID supplierUuid) {
        this.supplierUuid = supplierUuid;
    }
}
