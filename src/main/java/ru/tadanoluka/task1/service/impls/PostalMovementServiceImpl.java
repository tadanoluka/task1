package ru.tadanoluka.task1.service.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tadanoluka.task1.controller.dto.request.ArrivePostalMovementRequestDTO;
import ru.tadanoluka.task1.controller.dto.request.DepartPostalMovementRequestDTO;
import ru.tadanoluka.task1.controller.dto.response.PostalMovementResponseDTO;
import ru.tadanoluka.task1.controller.exception.exceptions.ConflictException;
import ru.tadanoluka.task1.controller.exception.exceptions.InternalServerErrorException;
import ru.tadanoluka.task1.mapper.PostalMovementMapper;
import ru.tadanoluka.task1.model.PostOffice;
import ru.tadanoluka.task1.model.PostalItem;
import ru.tadanoluka.task1.model.PostalMovement;
import ru.tadanoluka.task1.model.enums.PostalItemStatus;
import ru.tadanoluka.task1.model.enums.PostalMovementType;
import ru.tadanoluka.task1.repository.PostalMovementRepository;
import ru.tadanoluka.task1.service.PostOfficeService;
import ru.tadanoluka.task1.service.PostalItemService;
import ru.tadanoluka.task1.service.PostalMovementService;

import java.util.Comparator;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostalMovementServiceImpl implements PostalMovementService {

    private final PostalMovementRepository postalMovementRepository;
    private final PostalMovementMapper postalMovementMapper;
    private final PostalItemService postalItemService;
    private final PostOfficeService postOfficeService;

    @Transactional
    @Override
    public PostalMovementResponseDTO departPostalMovement(DepartPostalMovementRequestDTO movementDTO) {
        UUID postalItemId = UUID.fromString(movementDTO.postalItemId());
        PostalItem postalItem = postalItemService.findPostalItemById(postalItemId);
        PostalMovement lastPostalMovement = postalItem.getMovements().stream()
                .max(Comparator.comparing(PostalMovement::getTimestamp))
                .orElseThrow(() -> new InternalServerErrorException(
                        "The postal item with ID '%s' does not have recorded movements in the system"
                                .formatted(postalItem.getId().toString())
                ));

        if (lastPostalMovement.getType() == PostalMovementType.DEPARTED
                || lastPostalMovement.getType() == PostalMovementType.ISSUED) {
            throw new ConflictException("The postal item with ID '%s' has already left the post office");
        }

        PostOffice currentPostOffice = lastPostalMovement.getPostOffice();

        PostalMovement departPostalMovement = PostalMovement.builder()
                .postalItem(postalItem)
                .postOffice(currentPostOffice)
                .type(PostalMovementType.DEPARTED)
                .build();

        postalMovementRepository.save(departPostalMovement);

        postalItem.setStatus(PostalItemStatus.IN_TRANSIT);

        return postalMovementMapper.toPostalMovementResponseDTOFromEntity(departPostalMovement);
    }

    @Transactional
    @Override
    public PostalMovementResponseDTO arrivePostalMovement(ArrivePostalMovementRequestDTO movementDTO) {
        UUID postalItemId = UUID.fromString(movementDTO.postalItemId());
        PostalItem postalItem = postalItemService.findPostalItemById(postalItemId);
        PostOffice arrivalPostOffice = postOfficeService.findPostOfficeByPostcode(movementDTO.arrivalPostcode());

        PostalMovement arrivePostalMovement = PostalMovement.builder()
                .postalItem(postalItem)
                .postOffice(arrivalPostOffice)
                .type(PostalMovementType.ARRIVED)
                .build();

        if (arrivalPostOffice.equals(postalItem.getRecipientPostOffice())) {
            postalItem.setStatus(PostalItemStatus.ARRIVED);
        }

        postalMovementRepository.save(arrivePostalMovement);

        return postalMovementMapper.toPostalMovementResponseDTOFromEntity(arrivePostalMovement);
    }
}
