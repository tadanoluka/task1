package ru.tadanoluka.task1.service.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tadanoluka.task1.controller.dto.request.IssuePostalItemRequestDTO;
import ru.tadanoluka.task1.controller.dto.request.RegisterNewPostalItemRequestDTO;
import ru.tadanoluka.task1.controller.dto.response.PostalItemResponseDTO;
import ru.tadanoluka.task1.controller.exception.exceptions.ConflictException;
import ru.tadanoluka.task1.controller.exception.exceptions.InternalServerErrorException;
import ru.tadanoluka.task1.controller.exception.exceptions.NotFoundException;
import ru.tadanoluka.task1.mapper.PostalItemMapper;
import ru.tadanoluka.task1.model.PostOffice;
import ru.tadanoluka.task1.model.PostalItem;
import ru.tadanoluka.task1.model.PostalMovement;
import ru.tadanoluka.task1.model.enums.PostalItemStatus;
import ru.tadanoluka.task1.model.enums.PostalMovementType;
import ru.tadanoluka.task1.repository.PostalItemRepository;
import ru.tadanoluka.task1.service.PostOfficeService;
import ru.tadanoluka.task1.service.PostalItemService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostalItemServiceImpl implements PostalItemService {

    private final PostalItemRepository postalItemRepository;
    private final PostalItemMapper postalItemMapper;

    private final PostOfficeService postOfficeService;


    @Transactional
    @Override
    public PostalItemResponseDTO registerNewPostalItem(RegisterNewPostalItemRequestDTO postalItemDTO) {
        PostOffice senderPostOffice = postOfficeService.findPostOfficeByPostcode(postalItemDTO.senderPostcode());

        PostalItem postalItem = postalItemMapper.toPostalItemFromRegisterNewPostalItemRequestDTO(postalItemDTO);
        PostalMovement initialPostalMovement = PostalMovement.builder()
                .postalItem(postalItem)
                .postOffice(senderPostOffice)
                .type(PostalMovementType.RECEIVED)
                .build();
        List<PostalMovement> movements = new ArrayList<>();
        movements.add(initialPostalMovement);
        postalItem.setMovements(movements);

        if (postalItem.getRecipientPostOffice().equals(senderPostOffice)) {
            postalItem.setStatus(PostalItemStatus.ARRIVED);
        } else {
            postalItem.setStatus(PostalItemStatus.ACCEPTED);
        }

        postalItemRepository.save(postalItem);
        return postalItemMapper.toPostalItemResponseDTOFromPostalItem(postalItem);
    }

    @Override
    public PostalItemResponseDTO getPostalItemDTOById(UUID id) {
        PostalItem postalItem = findPostalItemById(id);
        return postalItemMapper.toPostalItemResponseDTOFromPostalItem(postalItem);
    }

    @Override
    public PostalItem findPostalItemById(UUID id) {
        return postalItemRepository.findPostalItemByIdOrderByMovementsTimestamp(id)
                .orElseThrow(() -> new NotFoundException(
                        "The postal item with id '%s' does not exists".formatted(id.toString())
                ));
    }

    @Transactional
    @Override
    public PostalItemResponseDTO issuePostalItem(IssuePostalItemRequestDTO issueDTO) {
        PostalItem postalItem = findPostalItemById(UUID.fromString(issueDTO.postalItemId()));

        PostalMovement lastPostalMovement = postalItem.getMovements().stream()
                .max(Comparator.comparing(PostalMovement::getTimestamp))
                .orElseThrow(() -> new InternalServerErrorException(
                        "The postal item with ID '%s' does not have recorded movements in the system"
                                .formatted(postalItem.getId().toString())
                ));

        if (!lastPostalMovement.getPostOffice().equals(postalItem.getRecipientPostOffice())
                || !lastPostalMovement.getType().equals(PostalMovementType.ARRIVED)) {
            throw new ConflictException("The postal item with ID '%s' has not yet arrived at the post office");
        }

        PostalMovement issuePostalMovement = PostalMovement.builder()
                .postalItem(postalItem)
                .postOffice(postalItem.getRecipientPostOffice())
                .type(PostalMovementType.ISSUED)
                .build();
        postalItem.getMovements().add(issuePostalMovement);
        postalItem.setStatus(PostalItemStatus.RECEIVED);

        postalItemRepository.save(postalItem);
        return postalItemMapper.toPostalItemResponseDTOFromPostalItem(postalItem);
    }
}
