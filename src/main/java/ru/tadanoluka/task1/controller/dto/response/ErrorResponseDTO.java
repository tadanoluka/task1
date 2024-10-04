package ru.tadanoluka.task1.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.Instant;


@Schema(description = "Error response object")
public record ErrorResponseDTO (
    @Schema(description = "URI of the request that caused the error",
            example = "http://localhost:8088/api/v1/postal-items/issue")
    String uri,

    @Schema(description = "HTTP status description",
            example = "Not Found")
    String status,

    @Schema(description = "Error message providing details about the issue",
            example = "Resource not found")
    String message,

    @Schema(description = "Timestamp when the error occurred",
            example = "2024-10-03T15:23:48.979197Z",
            type = "string",
            format = "date-time")
    String timestamp
) {
    public ErrorResponseDTO(String status, String message) {
        this(ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString(), status,
                message, Instant.now().toString());
    }
}
