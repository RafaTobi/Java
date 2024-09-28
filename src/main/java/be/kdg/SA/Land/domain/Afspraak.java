package be.kdg.SA.Land.domain;

import jakarta.persistence.*;

import java.util.UUID;
@Entity
public class Afspraak {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
   private UUID afspraakId;
    @OneToOne
    private Leverancier leverancier;
    @OneToOne
   private Vrachtwagen vrachtwagen;
    @OneToOne
   private Grondstof grondstof;
    @OneToOne
   private AankomstVenster aankomstVenster;

    protected Afspraak() {} //voor jpa
    public Afspraak(Leverancier leverancier , Vrachtwagen vrachtwagen, Grondstof grondstof, AankomstVenster aankomstVenster) {
        this.leverancier = leverancier;
        this.vrachtwagen = vrachtwagen;
        this.grondstof = grondstof;
        this.aankomstVenster = aankomstVenster;
    }

    public UUID getAfspraakId() {
        return afspraakId;
    }

    public Vrachtwagen getVrachtwagen() {
        return vrachtwagen;
    }

    public Grondstof getGrondstof() {
        return grondstof;
    }

    public AankomstVenster getAankomstVenster() {
        return aankomstVenster;
    }
}
