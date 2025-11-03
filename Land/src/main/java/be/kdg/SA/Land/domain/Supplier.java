package be.kdg.sa.land.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String address;
    private String phonenumber;

    protected Supplier() {} //voor jpa
    public Supplier(String name, String address, String phonenumber) {
        this.name = name;
        this.address = address;
        this.phonenumber = phonenumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "naam='" + name + '\'' +
                ", adres='" + address + '\'' +
                ", telefoonnummer='" + phonenumber + '\'' +
                '}';
    }
}
