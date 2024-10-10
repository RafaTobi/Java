package be.kdg.SA.Land.controller.Dto;

import be.kdg.SA.Land.domain.*;


import java.util.UUID;

public record AppointmentDto(UUID appointmentId, Supplier supplier, Truck truck, Resource resource, ArrivalWindow arrivalWindow){
}
