package ru.tadanoluka.task1.service.impls;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tadanoluka.task1.controller.dto.request.CreatePostOfficeRequestDTO;
import ru.tadanoluka.task1.controller.dto.response.PostOfficeResponseDTO;
import ru.tadanoluka.task1.controller.exception.exceptions.NotFoundException;
import ru.tadanoluka.task1.controller.exception.exceptions.UniqueConstraintViolationException;
import ru.tadanoluka.task1.mapper.PostOfficeMapperImpl;
import ru.tadanoluka.task1.model.PostOffice;
import ru.tadanoluka.task1.repository.PostOfficeRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PostOfficeServiceImplTest {

    @Mock
    private PostOfficeRepository postOfficeRepository;
    @Spy
    private PostOfficeMapperImpl postOfficeMapper;

    @InjectMocks
    private PostOfficeServiceImpl postOfficeService;

    private final PostOffice testPostOffice = PostOffice.builder()
            .id(UUID.randomUUID())
            .postcode("123456")
            .name("Test name 1")
            .address("Test address 1")
            .build();

    @Test
    void createPostOffice_whenValidRequest_shouldReturnSavedPostOfficeDTO() {
        CreatePostOfficeRequestDTO requestDTO = new CreatePostOfficeRequestDTO(
                testPostOffice.getPostcode(), testPostOffice.getName(), testPostOffice.getAddress()
        );
        PostOfficeResponseDTO expectedDTO = postOfficeMapper.toPostOfficeResponseDTO(testPostOffice);

        when(postOfficeRepository.existsByPostcode(anyString())).thenReturn(false);
        when(postOfficeRepository.save(any())).thenReturn(testPostOffice);

        PostOfficeResponseDTO responseDTO = postOfficeService.createPostOffice(requestDTO);
        assertNotNull(responseDTO);
        assertEquals(expectedDTO, responseDTO);
        verify(postOfficeRepository, times(1)).existsByPostcode(anyString());
        verify(postOfficeRepository, times(1)).save(any());
    }

    @Test
    void createPostOffice_whenPostcodeConflicts_shouldThrowUniqueConstraintViolationException() {
        CreatePostOfficeRequestDTO requestDTO = new CreatePostOfficeRequestDTO(
                testPostOffice.getPostcode(), testPostOffice.getName(), testPostOffice.getAddress()
        );

        when(postOfficeRepository.existsByPostcode(anyString())).thenReturn(true);

        assertThrows(UniqueConstraintViolationException.class, () -> postOfficeService.createPostOffice(requestDTO));
        verify(postOfficeRepository, times(1)).existsByPostcode(anyString());
        verify(postOfficeRepository, never()).save(any());
        verifyNoMoreInteractions(postOfficeRepository);
    }

    @Test
    void getPostOfficesDTOList_whenDBIsNotEmpty_shouldReturnPostOfficeDTOList() {
        List<PostOfficeResponseDTO> expectedList = List.of(postOfficeMapper.toPostOfficeResponseDTO(testPostOffice));

        when(postOfficeRepository.findAll()).thenReturn(List.of(testPostOffice));

        List<PostOfficeResponseDTO> responseList = postOfficeService.getPostOfficesDTOList();
        assertNotNull(responseList);
        assertEquals(expectedList, responseList);
        verify(postOfficeRepository, times(1)).findAll();
    }

    @Test
    void getPostOfficesDTOList_whenDBIsEmpty_shouldReturnEmptyList() {
        when(postOfficeRepository.findAll()).thenReturn(Collections.emptyList());

        List<PostOfficeResponseDTO> responseList = postOfficeService.getPostOfficesDTOList();
        assertNotNull(responseList);
        assertTrue(responseList.isEmpty());
        verify(postOfficeRepository, times(1)).findAll();
    }

    @Test
    void getPostOfficeDTOByPostcode_whenPostOfficeExists_shouldReturnPostOfficeDTO() {
        PostOfficeResponseDTO expectedDTO = postOfficeMapper.toPostOfficeResponseDTO(testPostOffice);

        when(postOfficeRepository.findByPostcode(testPostOffice.getPostcode()))
                .thenReturn(Optional.of(testPostOffice));

        PostOfficeResponseDTO responseDTO = postOfficeService.getPostOfficeDTOByPostcode(testPostOffice.getPostcode());
        assertNotNull(responseDTO);
        assertEquals(expectedDTO, responseDTO);
        verify(postOfficeRepository, times(1)).findByPostcode(anyString());
    }

    @Test
    void getPostOfficeDTOByPostcode_whenPostOfficeDoesNotExists_shouldThrowNotFoundException() {
        when(postOfficeRepository.findByPostcode(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> postOfficeService.getPostOfficeDTOByPostcode("123456"));
        verify(postOfficeRepository, times(1)).findByPostcode(anyString());
    }

    @Test
    void findPostOfficeByPostcode_whenPostOfficeExists_shouldReturnPostOffice() {
        when(postOfficeRepository.findByPostcode(testPostOffice.getPostcode()))
                .thenReturn(Optional.of(testPostOffice));

        PostOffice postOfficeFromDB = postOfficeService.findPostOfficeByPostcode(testPostOffice.getPostcode());

        assertNotNull(postOfficeFromDB);
        assertEquals(testPostOffice, postOfficeFromDB);
        verify(postOfficeRepository, times(1)).findByPostcode(anyString());
    }

    @Test
    void findPostOfficeByPostcode_whenPostOfficeDoesNotExist_shouldThrowNotFoundException() {
        when(postOfficeRepository.findByPostcode(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> postOfficeService.findPostOfficeByPostcode("123456"));
        verify(postOfficeRepository, times(1)).findByPostcode(anyString());
    }
}