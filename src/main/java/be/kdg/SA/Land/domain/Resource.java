package be.kdg.SA.Land.domain;

import jakarta.persistence.*;
import be.kdg.SA.Land.domain.enums.*;
import java.util.UUID;

@Entity
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ResourceId;
    @Enumerated(EnumType.STRING)
    private ResourceType name;
    private String Description;
    private int StoragePrice;
    private int ProductPrice;

    protected Resource() {} //voor jpa
    public Resource(UUID ResourceId, String Description, int StoragePrice, int ProductPrice, ResourceType name) {
        this.ResourceId = ResourceId;
        this.Description = Description;
        this.StoragePrice = StoragePrice;
        this.ProductPrice = ProductPrice;
        this.name = name;
    }

    public UUID getResourceId() {
        return ResourceId;
    }

    public ResourceType getName() {
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
