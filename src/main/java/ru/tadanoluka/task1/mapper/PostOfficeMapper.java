package ru.tadanoluka.task1.mapper;

import org.mapstruct.Mapper;
import ru.tadanoluka.task1.controller.dto.request.CreatePostOfficeRequestDTO;
import ru.tadanoluka.task1.controller.dto.response.PostOfficeResponseDTO;
import ru.tadanoluka.task1.model.PostOffice;

@Mapper(componentModel = "spring")
public interface PostOfficeMapper {

    PostOffice toPostOfficeFromDTO(CreatePostOfficeRequestDTO postOfficeDTO);

    PostOfficeResponseDTO toPostOfficeResponseDTO(PostOffice postOffice);
}
