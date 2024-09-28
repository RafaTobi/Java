package be.kdg.SA.Land.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
public class AankomstVenster {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID vensterId;
    private LocalDateTime aankomstTijd;
    private LocalDateTime vertrekTijd;
}
