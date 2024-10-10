package be.kdg.SA.Land.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID GrondstofId;
    private String Beschrijving;
    private int OpslagPrijs;
    private int ProductPrijs;

    protected Resource() {} //voor jpa
    public Resource(UUID GrondstofId, String Beschrijving, int OpslagPrijs, int ProductPrijs) {
        this.GrondstofId = GrondstofId;
        this.Beschrijving = Beschrijving;
        this.OpslagPrijs = OpslagPrijs;
        this.ProductPrijs = ProductPrijs;
    }

    public UUID getGrondstofId() {
        return GrondstofId;
    }

    public String getBeschrijving() {
        return Beschrijving;
    }

    public int getOpslagPrijs() {
        return OpslagPrijs;
    }

    public int getProductPrijs() {
        return ProductPrijs;
    }
}
