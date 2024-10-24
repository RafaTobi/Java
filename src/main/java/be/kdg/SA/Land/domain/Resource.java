package be.kdg.SA.Land.domain;

import jakarta.persistence.*;
import be.kdg.SA.Land.domain.enums.*;
import java.util.UUID;

@Entity
public class Resource {
    @Id
    @Enumerated(EnumType.STRING)
    private ResourceType name;

    private String Description;
    private int StoragePrice;
    private int ProductPrice;

    protected Resource() {} //voor jpa
    public Resource( String Description, int StoragePrice, int ProductPrice, ResourceType name) {
        this.Description = Description;
        this.StoragePrice = StoragePrice;
        this.ProductPrice = ProductPrice;
        this.name = name;
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
