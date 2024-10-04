package ru.tadanoluka.task1.service;

import ru.tadanoluka.task1.controller.dto.request.IssuePostalItemRequestDTO;
import ru.tadanoluka.task1.controller.dto.request.RegisterNewPostalItemRequestDTO;
import ru.tadanoluka.task1.controller.dto.response.PostalItemResponseDTO;
import ru.tadanoluka.task1.model.PostalItem;

import java.util.UUID;

public interface PostalItemService {
    PostalItemResponseDTO registerNewPostalItem(RegisterNewPostalItemRequestDTO postalItemDTO);

    PostalItemResponseDTO getPostalItemDTOById(UUID id);

    PostalItem findPostalItemById(UUID id);

    PostalItemResponseDTO issuePostalItem(IssuePostalItemRequestDTO issueDTO);
}
