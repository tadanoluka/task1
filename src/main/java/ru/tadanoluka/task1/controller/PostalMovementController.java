package ru.tadanoluka.task1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.tadanoluka.task1.controller.dto.request.ArrivePostalMovementRequestDTO;
import ru.tadanoluka.task1.controller.dto.request.DepartPostalMovementRequestDTO;
import ru.tadanoluka.task1.controller.dto.response.ErrorResponseDTO;
import ru.tadanoluka.task1.controller.dto.response.PostalMovementResponseDTO;
import ru.tadanoluka.task1.service.PostalMovementService;

@RestController
@RequestMapping("/api/v1/postal-movements")
@RequiredArgsConstructor
@Validated
@Tag(name = "Postal Movement")
public class PostalMovementController {

    private final PostalMovementService postalMovementService;


    @Operation(summary = "Depart a postal item from a post office")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Postal movement successfully registered",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostalMovementResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Postal item or post office not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)))})
    @PostMapping("/depart")
    @ResponseStatus(HttpStatus.CREATED)
    public PostalMovementResponseDTO departPostalMovement(@RequestBody @Valid DepartPostalMovementRequestDTO movementDTO) {
        return postalMovementService.departPostalMovement(movementDTO);
    }

    @Operation(summary = "Register arrival of a postal item at a post office")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Postal movement arrival successfully registered",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostalMovementResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Postal item or post office not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)))})
    @PostMapping("/arrive")
    @ResponseStatus(HttpStatus.CREATED)
    public PostalMovementResponseDTO arrivePostalMovement(@RequestBody @Valid ArrivePostalMovementRequestDTO movementDTO) {
        return postalMovementService.arrivePostalMovement(movementDTO);
    }
}
