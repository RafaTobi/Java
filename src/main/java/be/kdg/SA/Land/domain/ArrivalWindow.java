package be.kdg.SA.Land.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class ArrivalWindow {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID vensterId;
    private LocalDateTime ArrivalTime;
    private LocalDateTime LeavingTime;

    public LocalDateTime getArrivalTime() {
        return ArrivalTime;
    }

    public LocalDateTime getLeavingTime() {
        return LeavingTime;
    }


}
