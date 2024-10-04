package ru.tadanoluka.task1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ru.tadanoluka.task1.controller.dto.response.PostalMovementResponseDTO;
import ru.tadanoluka.task1.model.PostalMovement;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostalMovementMapper {

    PostalMovementResponseDTO toPostalMovementResponseDTOFromEntity(PostalMovement postalMovement);

    @Named("toPostalMovementResponseDTOFromEntityList")
    List<PostalMovementResponseDTO> toPostalMovementResponseDTOFromEntityList(List<PostalMovement> postalMovements);
}
