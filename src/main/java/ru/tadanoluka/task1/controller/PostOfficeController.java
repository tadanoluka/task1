package ru.tadanoluka.task1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.tadanoluka.task1.controller.dto.request.CreatePostOfficeRequestDTO;
import ru.tadanoluka.task1.controller.dto.response.ErrorResponseDTO;
import ru.tadanoluka.task1.controller.dto.response.PostOfficeResponseDTO;
import ru.tadanoluka.task1.service.PostOfficeService;
import ru.tadanoluka.task1.validation.annotation.Postcode;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post-offices")
@RequiredArgsConstructor
@Validated
@Tag(name = "Post Offices")
public class PostOfficeController {

    private final PostOfficeService postOfficeService;

    @Operation(summary = "Add a new post office")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "The request has been processed successfully " +
                            "and the post office has been added.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PostOfficeResponseDTO.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "The request could not be processed due to validation errors.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(
                    responseCode = "409",
                    description = "The request could not be processed due to a data conflict. " +
                            "For example, the postcode you entered was already in use by another post office.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDTO.class)))})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostOfficeResponseDTO createPostOffice(@RequestBody @Valid CreatePostOfficeRequestDTO postOfficeDTO) {
        return postOfficeService.createPostOffice(postOfficeDTO);
    }

    @Operation(summary = "Get a list of post offices")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the list of post offices",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(
                                    schema = @Schema(
                                            description = "List of post offices",
                                            implementation = PostOfficeResponseDTO.class))))})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostOfficeResponseDTO> getPostOfficesList() {
        return postOfficeService.getPostOfficesDTOList();
    }

    @Operation(summary = "Get information about a specific post office by its postcode")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved post office information",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostOfficeResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid postcode format",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Post office not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)))})
    @GetMapping("/{postcode}")
    @ResponseStatus(HttpStatus.OK)
    public PostOfficeResponseDTO getPostOfficeByPostcode(
            @Parameter(description = "Postcode of the post office", example = "123456", in = ParameterIn.PATH)
            @PathVariable @Postcode String postcode) {
        return postOfficeService.getPostOfficeDTOByPostcode(postcode);
    }
}
