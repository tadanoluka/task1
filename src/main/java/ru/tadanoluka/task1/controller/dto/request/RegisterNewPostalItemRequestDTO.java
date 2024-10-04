package ru.tadanoluka.task1.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.tadanoluka.task1.model.enums.PostalItemType;
import ru.tadanoluka.task1.validation.annotation.EnumValue;
import ru.tadanoluka.task1.validation.annotation.Postcode;

@Schema(description = "Request DTO for registering a new postal item")
public record RegisterNewPostalItemRequestDTO(
        @NotBlank
        @Schema(description = "Name of the recipient",
                example = "Test User",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String recipientName,

        @NotNull
        @Postcode
        @Schema(description = "Postcode of the recipient, must be exactly 6 digits",
                example = "123456",
                requiredMode = Schema.RequiredMode.REQUIRED,
                pattern = "^\\d{6}$")
        String recipientPostcode,

        @NotBlank
        @Schema(description = "Address of the recipient",
                example = "123 Main St, Cityville",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String recipientAddress,

        @NotNull
        @Postcode
        @Schema(description = "Postcode of the sender, must be exactly 6 digits",
                example = "654321",
                requiredMode = Schema.RequiredMode.REQUIRED,
                pattern = "^\\d{6}$")
        String senderPostcode,

        @NotNull
        @EnumValue(enumClass = PostalItemType.class, message = "{validation.enum.PostalItemType}")
        @Schema(description = "Type of postal item",
                example = "PACKAGE",
                requiredMode = Schema.RequiredMode.REQUIRED,
                implementation = PostalItemType.class)
        String type) {
}
