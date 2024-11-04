package be.kdg.sa.land.service;

import be.kdg.sa.land.domain.ArrivalWindow;
import be.kdg.sa.land.repository.ArrivalWindowRepository;
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

        LocalTime startOfBusiness = LocalTime.of(6, 0);
        LocalTime endOfBusiness = LocalTime.of(20, 0);

        LocalTime currentSlot = startOfBusiness;

        while (currentSlot.isBefore(endOfBusiness)) {
            ArrivalWindow slot = ArrivalWindow.createOneHourSlot(currentSlot);
            timeSlots.add(slot);
            currentSlot = currentSlot.plusHours(1);
        }

        return timeSlots;
    }


    public ArrivalWindow createArrivalWindow(Date date, LocalTime startTime) {
        LocalTime endTime = startTime.plusHours(1);
        ArrivalWindow arrivalWindow = new ArrivalWindow(startTime, endTime);
        arrivalWindow.setDate(date);

        return arrivalWindow;
    }


}

