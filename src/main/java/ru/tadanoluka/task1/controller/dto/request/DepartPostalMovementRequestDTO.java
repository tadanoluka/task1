package ru.tadanoluka.task1.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;

@Schema(description = "Request DTO for registering the departure of a postal item")
public record DepartPostalMovementRequestDTO(
        @NotNull
        @UUID
        @Schema(description = "UUID of the postal item",
                example = "123e4567-e89b-12d3-a456-426614174000",
                requiredMode = Schema.RequiredMode.REQUIRED,
                format = "uuid")
        String postalItemId) {
}
