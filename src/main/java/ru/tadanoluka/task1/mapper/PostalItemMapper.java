package ru.tadanoluka.task1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tadanoluka.task1.controller.dto.request.RegisterNewPostalItemRequestDTO;
import ru.tadanoluka.task1.controller.dto.response.PostalItemResponseDTO;
import ru.tadanoluka.task1.model.PostalItem;
import ru.tadanoluka.task1.service.PostOfficeService;

@Mapper(componentModel = "spring", uses = {PostOfficeService.class, PostalMovementMapper.class})
public interface PostalItemMapper {

    @Mapping(target = "recipientPostOffice", source = "recipientPostcode", qualifiedByName = "findPostOfficeByPostcode")
    PostalItem toPostalItemFromRegisterNewPostalItemRequestDTO(RegisterNewPostalItemRequestDTO postalItemDTO);

    @Mapping(target = "movements", source = "movements", qualifiedByName = "toPostalMovementResponseDTOFromEntityList")
    @Mapping(target = "recipientPostcode", source = "recipientPostOffice.postcode")
    PostalItemResponseDTO toPostalItemResponseDTOFromPostalItem(PostalItem postalItem);

}
