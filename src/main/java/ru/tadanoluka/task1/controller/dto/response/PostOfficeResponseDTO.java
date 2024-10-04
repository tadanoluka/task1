package ru.tadanoluka.task1.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Response DTO for Post Office")
public record PostOfficeResponseDTO(
        @Schema(description = "Unique identifier of the post office",
                example = "33a278d9-5434-485f-ab02-7a524ba722fd")
        UUID id,

        @Schema(description = "Postcode of the post office, must be exactly 6 digits",
                example = "123456",
                pattern = "^\\d{6}$")
        String postcode,

        @Schema(description = "Name of the post office",
                example = "Central Post Office")
        String name,

        @Schema(description = "Address of the post office",
                example = "123 Main St, Cityville")
        String address) {
}
