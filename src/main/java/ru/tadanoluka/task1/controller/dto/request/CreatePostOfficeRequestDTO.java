package ru.tadanoluka.task1.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.tadanoluka.task1.validation.annotation.Postcode;

@Schema(description = "DTO for creating a new post office")
public record CreatePostOfficeRequestDTO(
        @NotNull
        @Postcode
        @Schema(description = "Postcode of the post office, must be exactly 6 digits",
                example = "260005",
                minLength = 6,
                maxLength = 6,
                pattern = "^\\d{6}$",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String postcode,

        @NotBlank
        @Schema(description = "Name of the post office",
                example = "Central Post Office",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String name,

        @NotBlank
        @Schema(description = "Address of the post office",
                example = "123 Main St, Cityville",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String address) {
}
