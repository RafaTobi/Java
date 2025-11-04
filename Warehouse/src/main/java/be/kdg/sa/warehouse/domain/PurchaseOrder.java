package be.kdg.sa.warehouse.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class PurchaseOrder {
    private String poNumber; //TODO format -> PO123456, mag ik er van uitgaan dat aanvragen het standaard juist doen??
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID referenceUuid;
    @ManyToOne
    private Purchaser costumerParty;
    @ManyToOne
    private Supplier sellerParty;
    private String vesselNumber; // format VSL7891011
    @OneToMany(mappedBy = "purchaseOrder")
    private List<OrderLine> orderLines;
    private boolean orderFulfilled = false;
    private LocalDateTime fulfillDate;

    // Voor Unit Testing
    public PurchaseOrder(UUID referenceUuid, Supplier sellerParty, List<OrderLine> orderLines, LocalDateTime fulfillDate) {
        this.referenceUuid = referenceUuid;
        this.sellerParty = sellerParty;
        this.orderLines = orderLines;
        this.fulfillDate = fulfillDate;
    }

    public PurchaseOrder() {
    }

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

    public Purchaser getCostumerParty() {
        return costumerParty;
    }

    public void setCostumerParty(Purchaser costumerParty) {
        this.costumerParty = costumerParty;
    }

    public Supplier getSellerParty() {
        return sellerParty;
    }

    public void setSellerParty(Supplier sellerParty) {
        this.sellerParty = sellerParty;
    }

    public String getVesselNumber() {
        return vesselNumber;
    }

    public void setVesselNumber(String vesselNumber) {
        this.vesselNumber = vesselNumber;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
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
