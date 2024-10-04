package ru.tadanoluka.task1.controller.exception;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.BindErrorUtils;
import ru.tadanoluka.task1.controller.dto.response.ErrorResponseDTO;
import ru.tadanoluka.task1.controller.exception.exceptions.ConflictException;
import ru.tadanoluka.task1.controller.exception.exceptions.InternalServerErrorException;
import ru.tadanoluka.task1.controller.exception.exceptions.NotFoundException;
import ru.tadanoluka.task1.controller.exception.exceptions.UniqueConstraintViolationException;

import java.util.stream.Collectors;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleConstraintViolationException(ConstraintViolationException e) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(".\n "));
        return new ErrorResponseDTO(HttpStatus.BAD_REQUEST.getReasonPhrase(), errorMessage);
    }

    @ExceptionHandler({UniqueConstraintViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDTO handleDuplicateKeyException(UniqueConstraintViolationException e) {
        return new ErrorResponseDTO(HttpStatus.CONFLICT.getReasonPhrase(), e.getMessage());
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO handleNotFoundException(NotFoundException e) {
        return new ErrorResponseDTO(HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDTO handleConflictException(ConflictException e) {
        return new ErrorResponseDTO(HttpStatus.CONFLICT.getReasonPhrase(), e.getMessage());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDTO handleInternalServerErrorException(InternalServerErrorException e) {
        return new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = BindErrorUtils.resolveAndJoin(e.getAllErrors());
        return new ErrorResponseDTO(HttpStatus.BAD_REQUEST.getReasonPhrase(), errorMessage);
    }
}
