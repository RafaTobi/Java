package be.kdg.SA.Land.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class ArrivalWindow {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID vensterId;
    private LocalDateTime aankomstTijd;
    private LocalDateTime vertrekTijd;

    public LocalDateTime getAankomstTijd() {
        return aankomstTijd;
    }

    public LocalDateTime getVertrekTijd() {
        return vertrekTijd;
    }
}
