package be.kdg.sa.warehouse.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long lineNumber;
    @ManyToOne
    private Material material;
    @ManyToOne
    private PurchaseOrder purchaseOrder;
    private double quantity;
    private final String uom = "kt";

    public OrderLine(){}

    public OrderLine(long lineNumber, Material material, double quantity) {
        this.lineNumber = lineNumber;
        this.material = material;
        this.quantity = quantity;
    }

    public void setLineNumber(long lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public long getLineNumber() {
        return lineNumber;
    }

    public Material getMaterial() {
        return material;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUom() {
        return uom;
    }
}
