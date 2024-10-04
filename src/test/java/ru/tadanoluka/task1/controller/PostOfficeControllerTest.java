package ru.tadanoluka.task1.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.tadanoluka.task1.controller.dto.response.PostOfficeResponseDTO;
import ru.tadanoluka.task1.controller.exception.exceptions.NotFoundException;
import ru.tadanoluka.task1.mapper.PostOfficeMapperImpl;
import ru.tadanoluka.task1.model.PostOffice;
import ru.tadanoluka.task1.service.impls.PostOfficeServiceImpl;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PostOfficeController.class)
@AutoConfigureMockMvc
class PostOfficeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @SpyBean
    private PostOfficeMapperImpl postOfficeMapper;
    @MockBean
    private PostOfficeServiceImpl postOfficeService;

    private final PostOffice testPostOffice = PostOffice.builder()
            .id(UUID.randomUUID())
            .postcode("123456")
            .name("Test name 1")
            .address("Test address 1")
            .build();

    @Test
    void getPostOfficeByPostcode_whenValidRequest_returnOKResponse() throws Exception {
        PostOfficeResponseDTO expectedDto = postOfficeMapper.toPostOfficeResponseDTO(testPostOffice);

        when(postOfficeService.getPostOfficeDTOByPostcode(anyString())).thenReturn(expectedDto);

        mockMvc.perform(get("/api/v1/post-offices/{postcode}", testPostOffice.getPostcode()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testPostOffice.getId().toString()))
                .andExpect(jsonPath("$.postcode").value(testPostOffice.getPostcode()));
    }

    @Test
    void getPostOfficeByPostcode_whenInvalidPostcode_returnBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/post-offices/{postcode}", "abc"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void getPostOfficeByPostcode_whenPostOfficeDoesNotExists_returnNotFound() throws Exception {
        when(postOfficeService.getPostOfficeDTOByPostcode(anyString())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/api/v1/post-offices/{postcode}", "123456"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}