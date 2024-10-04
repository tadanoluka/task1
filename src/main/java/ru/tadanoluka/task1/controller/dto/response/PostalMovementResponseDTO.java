package ru.tadanoluka.task1.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.tadanoluka.task1.model.enums.PostalMovementType;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "Response DTO for postal movement")
public record PostalMovementResponseDTO(
        @Schema(description = "UUID of the postal movement",
                example = "123e4567-e89b-12d3-a456-426614174000",
                type = "string",
                format = "uuid")
        UUID id,

        @Schema(description = "Information about the post office involved in the movement",
                implementation = PostOfficeResponseDTO.class)
        PostOfficeResponseDTO postOffice,

        @Schema(description = "Type of the postal movement",
                example = "RECEIVED",
                implementation = PostalMovementType.class)
        PostalMovementType type,

        @Schema(description = "Timestamp of the postal movement",
                example = "2024-10-03T15:23:48.979197Z",
                type = "string",
                format = "date-time")
        Instant timestamp) {
}
