package be.kdg.SA.Land.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String licenseplate;
    private int laadvermogen;
    @OneToOne
    private WeighBridgeTicket wbTicket;

    protected Truck() {} //voor jpa
    public Truck(String licenseplate, int laadvermogen) {
        this.licenseplate = licenseplate;
        this.laadvermogen = laadvermogen;
    }

    public String getLicenseplate() {
        return licenseplate;
    }

    public void setLicenseplate(String licenseplate) {
        this.licenseplate = licenseplate;
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
                "nummerplaat='" + licenseplate + '\'' +
                ", laadvermogen=" + laadvermogen +
                '}';
    }
}
