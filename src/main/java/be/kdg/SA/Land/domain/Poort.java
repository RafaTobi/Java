package be.kdg.SA.Land.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Poort {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany
    private List<Vrachtwagen> vrachtwagen = new ArrayList<>();

    protected Poort(){}
}
