package ru.tadanoluka.task1.controller.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import ru.tadanoluka.task1.model.enums.PostalItemStatus;
import ru.tadanoluka.task1.model.enums.PostalItemType;

import java.util.List;
import java.util.UUID;

@Schema(description = "Response DTO for postal item")
public record PostalItemResponseDTO(
        @Schema(description = "UUID of the postal item",
                example = "123e4567-e89b-12d3-a456-426614174000",
                type = "string",
                format = "uuid")
        UUID id,
        @Schema(description = "Type of postal item",
                example = "PACKAGE",
                type = "string",
                implementation = PostalItemType.class)
        PostalItemType type,

        @Schema(description = "Name of the recipient",
                example = "Test User")
        String recipientName,

        @Schema(description = "Postcode of the recipient, must be exactly 6 digits",
                example = "123456",
                pattern = "^\\d{6}$")
        String recipientPostcode,

        @Schema(description = "Address of the recipient",
                example = "123 Main St, Cityville")
        String recipientAddress,

        @Schema(description = "Status of postal item",
                example = "IN_TRANSIT",
                type = "string",
                implementation = PostalItemStatus.class)
        PostalItemStatus status,

        @ArraySchema(schema = @Schema(description = "List of postal movements associated with this postal item",
                implementation = PostalMovementResponseDTO.class))
        List<PostalMovementResponseDTO> movements) {
}
