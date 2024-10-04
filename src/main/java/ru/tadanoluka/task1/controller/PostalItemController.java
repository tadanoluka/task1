package ru.tadanoluka.task1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.tadanoluka.task1.controller.dto.request.IssuePostalItemRequestDTO;
import ru.tadanoluka.task1.controller.dto.request.RegisterNewPostalItemRequestDTO;
import ru.tadanoluka.task1.controller.dto.response.ErrorResponseDTO;
import ru.tadanoluka.task1.controller.dto.response.PostalItemResponseDTO;
import ru.tadanoluka.task1.service.PostalItemService;

@RestController
@RequestMapping("/api/v1/postal-items")
@RequiredArgsConstructor
@Validated
@Tag(name = "Postal Item")
public class PostalItemController {

    private final PostalItemService postalItemService;

    @Operation(summary = "Register a new postal item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Postal item successfully registered",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostalItemResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Post office from request data does not exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class))),
    })
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public PostalItemResponseDTO registerNewPostalItem(@RequestBody @Valid RegisterNewPostalItemRequestDTO postalItemDTO) {
        return postalItemService.registerNewPostalItem(postalItemDTO);
    }

    @Operation(summary = "Get information about a specific postal item by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Postal item found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostalItemResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid postal item ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Postal item not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)))})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostalItemResponseDTO getPostalItemById(@PathVariable @UUID String id) {
        return postalItemService.getPostalItemDTOById(java.util.UUID.fromString(id));
    }

    @Operation(summary = "Issue a postal item to the client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Postal item successfully issued",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostalItemResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Postal item not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "Postal item cannot be issued (not ready)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)))})
    @PostMapping("/issue")
    @ResponseStatus(HttpStatus.OK)
    public PostalItemResponseDTO issuePostalItem(@RequestBody @Valid IssuePostalItemRequestDTO issueDTO) {
        return postalItemService.issuePostalItem(issueDTO);
    }
}
