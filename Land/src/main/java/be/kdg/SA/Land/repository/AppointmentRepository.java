package be.kdg.sa.land.repository;

import be.kdg.sa.land.domain.Appointment;
import be.kdg.sa.land.domain.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    Optional<Appointment> findByAppointmentId(UUID appointmentId);

    Optional<List<Appointment>> findAppointmentByTruck(Truck truck);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Appointment a WHERE a.truck = :truck AND :currentTime BETWEEN a.arrivalWindow.startTime AND a.arrivalWindow.endTime")
    boolean existsByTruckAndArrivalWindowTime(Truck truck, LocalTime currentTime);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE " +
            "a.arrivalWindow.date = :arrivalDate AND " +
            "a.arrivalWindow.startTime <= :startTime AND " +
            "a.arrivalWindow.endTime >= :endTime")
    long countAppointmentsWithinDateAndTime(@Param("arrivalDate") LocalDate arrivalDate,
                                            @Param("startTime") LocalTime startTime,
                                            @Param("endTime") LocalTime endTime);

    @Query("SELECT a FROM Appointment a WHERE a.truck.licenseplate = :licensePlate AND :now BETWEEN a.arrivalWindow.startTime AND a.arrivalWindow.endTime")
    Appointment findAppointmentByTruckLicensePlate(String licensePlate, LocalTime now);
}