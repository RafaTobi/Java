package be.kdg.SA.Land.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String nummerplaat;
    private int laadvermogen;


    protected Truck() {} //voor jpa
    public Truck(String nummerplaat, int laadvermogen) {
        this.nummerplaat = nummerplaat;
        this.laadvermogen = laadvermogen;
    }

    public String getNummerplaat() {
        return nummerplaat;
    }

    public void setNummerplaat(String nummerplaat) {
        this.nummerplaat = nummerplaat;
    }

    public int getLaadvermogen() {
        return laadvermogen;
    }

    public void setLaadvermogen(int laadvermogen) {
        this.laadvermogen = laadvermogen;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "nummerplaat='" + nummerplaat + '\'' +
                ", laadvermogen=" + laadvermogen +
                '}';
    }
}
