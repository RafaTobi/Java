package be.kdg.SA.Land.controller.Dto;

import be.kdg.SA.Land.domain.*;
import jakarta.validation.constraints.NotBlank;

public record CreateAppointmentRequest(
        @NotBlank Supplier supplier,
        @NotBlank Truck truck,
        @NotBlank Resource resource,
        @NotBlank ArrivalWindow arrivalWindow) {
}
