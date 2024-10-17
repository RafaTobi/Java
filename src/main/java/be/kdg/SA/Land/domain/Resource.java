package be.kdg.SA.Land.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ResourceId;
    private String name;
    private String Description;
    private int StoragePrice;
    private int ProductPrice;

    protected Resource() {} //voor jpa
    public Resource(UUID ResourceId, String Description, int StoragePrice, int ProductPrice, String name) {
        this.ResourceId = ResourceId;
        this.Description = Description;
        this.StoragePrice = StoragePrice;
        this.ProductPrice = ProductPrice;
        this.name = name;
    }

    public UUID getResourceId() {
        return ResourceId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return Description;
    }

    public int getStoragePrice() {
        return StoragePrice;
    }

    public int getProductPrice() {
        return ProductPrice;
    }
}
