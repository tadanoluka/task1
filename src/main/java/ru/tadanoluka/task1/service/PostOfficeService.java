package ru.tadanoluka.task1.service;

import org.mapstruct.Named;
import ru.tadanoluka.task1.controller.dto.request.CreatePostOfficeRequestDTO;
import ru.tadanoluka.task1.controller.dto.response.PostOfficeResponseDTO;
import ru.tadanoluka.task1.model.PostOffice;

import java.util.List;

public interface PostOfficeService {

    PostOfficeResponseDTO createPostOffice(CreatePostOfficeRequestDTO postOfficeDTO);

    List<PostOfficeResponseDTO> getPostOfficesDTOList();

    PostOfficeResponseDTO getPostOfficeDTOByPostcode(String postcode);

    @Named("findPostOfficeByPostcode")
    PostOffice findPostOfficeByPostcode(String postcode);
}
