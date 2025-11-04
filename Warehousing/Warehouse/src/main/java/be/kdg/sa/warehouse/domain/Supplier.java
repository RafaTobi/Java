package be.kdg.sa.warehouse.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private String name;
    private String address;
    @OneToMany(mappedBy = "supplier")
    private List<Warehouse> warehouses = new ArrayList<>();

    public Supplier() {
    }

    public Supplier(UUID uuid, String name, String address, List<Warehouse> warehouses) {
        this.uuid = uuid;
        this.name = name;
        this.address = address;
        this.warehouses = warehouses;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }
}
