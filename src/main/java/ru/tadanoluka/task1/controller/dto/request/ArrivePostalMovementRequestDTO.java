package ru.tadanoluka.task1.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;
import ru.tadanoluka.task1.validation.annotation.Postcode;

@Schema(description = "Request DTO for registering the arrival of a postal item")
public record ArrivePostalMovementRequestDTO(
        @NotNull
        @UUID
        @Schema(description = "UUID of the postal item",
                example = "123e4567-e89b-12d3-a456-426614174000",
                requiredMode = Schema.RequiredMode.REQUIRED,
                format = "uuid")
        String postalItemId,

        @NotNull
        @Postcode
        @Schema(description = "Postcode of the arrival post office, must be exactly 6 digits",
                example = "123456",
                requiredMode = Schema.RequiredMode.REQUIRED,
                pattern = "^\\d{6}$")
        String arrivalPostcode) {
}
