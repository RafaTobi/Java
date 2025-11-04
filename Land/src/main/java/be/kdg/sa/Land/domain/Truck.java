package be.kdg.sa.land.domain;

import jakarta.persistence.*;

import java.util.List;
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
    @OneToMany(mappedBy = "truck")
    private List<Appointment> appointments;
    private boolean onSite;

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

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public WeighBridgeTicket getWbTicket() {
        return wbTicket;
    }

    public void setWbTicket(WeighBridgeTicket wbTicket) {
        this.wbTicket = wbTicket;
    }

    public boolean isOnSite() {
        return onSite;
    }

    public void setOnSite(boolean onSite) {
        this.onSite = onSite;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "nummerplaat='" + licenseplate + '\'' +
                ", laadvermogen=" + laadvermogen +
                '}';
    }
}
