package ru.tadanoluka.task1.service;

import ru.tadanoluka.task1.controller.dto.request.ArrivePostalMovementRequestDTO;
import ru.tadanoluka.task1.controller.dto.request.DepartPostalMovementRequestDTO;
import ru.tadanoluka.task1.controller.dto.response.PostalMovementResponseDTO;

public interface PostalMovementService {
    PostalMovementResponseDTO departPostalMovement(DepartPostalMovementRequestDTO movementDTO);

    PostalMovementResponseDTO arrivePostalMovement(ArrivePostalMovementRequestDTO movementDTO);
}
