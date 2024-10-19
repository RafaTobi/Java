// ArrivalWindow.java
package be.kdg.SA.Land.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@Entity
public class ArrivalWindow {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;

    protected ArrivalWindow() {} // for JPA
    public ArrivalWindow(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public static ArrivalWindow createOneHourSlot(LocalTime startTime) {
        return new ArrivalWindow(startTime, startTime.plusHours(1));
    }

    public String getTimeslot() {
        return startTime.toString() + " - " + endTime.toString();
    }
}