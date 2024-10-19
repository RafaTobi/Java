package be.kdg.SA.Land.service;

import be.kdg.SA.Land.domain.ArrivalWindow;
import be.kdg.SA.Land.domain.Truck;
import be.kdg.SA.Land.repository.ArrivalWindowRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;

@Service
public class ArrivalWindowService {

    private final ArrivalWindowRepository arrivalWindowRepository;

    public ArrivalWindowService(ArrivalWindowRepository arrivalWindowRepository) {
        this.arrivalWindowRepository = arrivalWindowRepository;
    }

    public List<ArrivalWindow> getAvailableTimeSlots() {
        List<ArrivalWindow> timeSlots = new ArrayList<>();

        LocalTime startOfBusiness = LocalTime.of(8, 0);  // Business opens at 8:00 AM
        LocalTime endOfBusiness = LocalTime.of(18, 0);   // Business closes at 6:00 PM

        LocalTime currentSlot = startOfBusiness;

        while (currentSlot.isBefore(endOfBusiness)) {
            ArrivalWindow slot = ArrivalWindow.createOneHourSlot(currentSlot);
            timeSlots.add(slot);
            currentSlot = currentSlot.plusHours(1);  // Move to the next 1-hour slot
        }

        return timeSlots;
    }


    public ArrivalWindow createArrivalWindow(Date date, LocalTime startTime) {
        LocalTime endTime = startTime.plusHours(1); // Assuming the slot is always 1 hour
        ArrivalWindow arrivalWindow = new ArrivalWindow(startTime, endTime);
        arrivalWindow.setDate(date);

        return arrivalWindow;
    }


}

