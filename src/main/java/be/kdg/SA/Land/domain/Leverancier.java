package be.kdg.SA.Land.domain;

import jakarta.persistence.*;

@Entity
public class Leverancier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String naam;
    private String adres;
    private String telefoonnummer;

    protected Leverancier() {} //voor jpa
    public Leverancier(String naam, String adres, String telefoonnummer) {
        this.naam = naam;
        this.adres = adres;
        this.telefoonnummer = telefoonnummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getTelefoonnummer() {
        return telefoonnummer;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    @Override
    public String toString() {
        return "Leverancier{" +
                "naam='" + naam + '\'' +
                ", adres='" + adres + '\'' +
                ", telefoonnummer='" + telefoonnummer + '\'' +
                '}';
    }
}
