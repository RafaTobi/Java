package be.kdg.sa.warehouse.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PurchaseOrderDto {
    private String poNumber;
    private UUID referenceUuid;
    @NotNull
    private PurchaserDto customerParty;
    @NotNull
    private SupplierDto sellerParty;
    private String vesselNumber;
    @NotNull
    private List<OrderLineDto> orderLines;
    private boolean orderFulfilled;
    private LocalDateTime fulfillDate;


    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public UUID getReferenceUuid() {
        return referenceUuid;
    }

    public void setReferenceUuid(UUID referenceUuid) {
        this.referenceUuid = referenceUuid;
    }

    public PurchaserDto getCustomerParty() {
        return customerParty;
    }

    public void setCustomerParty(PurchaserDto customerParty) {
        this.customerParty = customerParty;
    }

    public SupplierDto getSellerParty() {
        return sellerParty;
    }

    public void setSellerParty(SupplierDto sellerParty) {
        this.sellerParty = sellerParty;
    }

    public String getVesselNumber() {
        return vesselNumber;
    }

    public void setVesselNumber(String vesselNumber) {
        this.vesselNumber = vesselNumber;
    }

    public List<OrderLineDto> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLineDto> orderLines) {
        this.orderLines = orderLines;
    }

    public boolean isOrderFulfilled() {
        return orderFulfilled;
    }

    public void setOrderFulfilled(boolean orderFulfilled) {
        this.orderFulfilled = orderFulfilled;
    }

    public LocalDateTime getFulfillDate() {
        return fulfillDate;
    }

    public void setFulfillDate(LocalDateTime fulfillDate) {
        this.fulfillDate = fulfillDate;
    }
}
