package ru.tadanoluka.task1.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.tadanoluka.task1.configuration.TestcontainersConfiguration;
import ru.tadanoluka.task1.controller.dto.request.CreatePostOfficeRequestDTO;
import ru.tadanoluka.task1.controller.dto.response.ErrorResponseDTO;
import ru.tadanoluka.task1.controller.dto.response.PostOfficeResponseDTO;
import ru.tadanoluka.task1.model.PostOffice;
import ru.tadanoluka.task1.repository.PostOfficeRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class PostOfficeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostOfficeRepository postOfficeRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final PostOffice preSavedTestOffice = PostOffice.builder()
            .postcode("123451")
            .name("Test post office 1")
            .address("Test address 1")
            .build();

    @BeforeEach
    void setUp() {
        postOfficeRepository.save(preSavedTestOffice);
    }

    @AfterEach
    void tearDown() {
        postOfficeRepository.deleteAll();
    }

    @Test
    void createPostOffice_whenRequestIsValid_shouldCreatePostOffice() throws Exception {
        CreatePostOfficeRequestDTO requestDTO = new CreatePostOfficeRequestDTO(
                "023456",
                "test1",
                "test1"
        );
        String requestJson = objectMapper.writeValueAsString(requestDTO);

        String jsonResponse = mockMvc.perform(post("/api/v1/post-offices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();
        PostOfficeResponseDTO responseDTO = objectMapper.readValue(jsonResponse, PostOfficeResponseDTO.class);

        assertNotNull(responseDTO);
        assertNotNull(responseDTO.id());
        assertEquals(requestDTO.postcode(), responseDTO.postcode());
        assertEquals(requestDTO.name(), responseDTO.name());
        assertEquals(requestDTO.address(), responseDTO.address());
    }

    @Test
    void createPostOffice_whenRequestIsNotValid_shouldReturnBadRequest() throws Exception {
        CreatePostOfficeRequestDTO requestDTO = new CreatePostOfficeRequestDTO(
                "023456",
                null,
                "test1"
        );
        String requestJson = objectMapper.writeValueAsString(requestDTO);

        String jsonResponse = mockMvc.perform(post("/api/v1/post-offices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorResponseDTO responseDTO = objectMapper.readValue(jsonResponse, ErrorResponseDTO.class);

        assertNotNull(responseDTO);
        assertNotNull(responseDTO.uri());
        assertNotNull(responseDTO.status());
        assertNotNull(responseDTO.message());
        assertNotNull(responseDTO.timestamp());
    }

    @Test
    void createPostOffice_whenPostcodeConflicts_shouldReturnConflict() throws Exception {
        CreatePostOfficeRequestDTO requestDTO = new CreatePostOfficeRequestDTO(
                preSavedTestOffice.getPostcode(),
                "test1",
                "test1"
        );
        String requestJson = objectMapper.writeValueAsString(requestDTO);

        String jsonResponse = mockMvc.perform(post("/api/v1/post-offices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorResponseDTO responseDTO = objectMapper.readValue(jsonResponse, ErrorResponseDTO.class);

        assertNotNull(responseDTO);
        assertNotNull(responseDTO.uri());
        assertNotNull(responseDTO.status());
        assertNotNull(responseDTO.message());
        assertNotNull(responseDTO.timestamp());

    }

    @Test
    void getPostOfficeByPostcode_whenPostcodeIsExists_shouldReturnPostOffice() throws Exception {
        String jsonResponse = mockMvc.perform(get(
                        "/api/v1/post-offices/{postcode}",
                        preSavedTestOffice.getPostcode()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();
        PostOfficeResponseDTO responseDTO = objectMapper.readValue(jsonResponse, PostOfficeResponseDTO.class);

        assertNotNull(responseDTO);
        assertNotNull(responseDTO.id());
        assertEquals(preSavedTestOffice.getPostcode(), responseDTO.postcode());
        assertEquals(preSavedTestOffice.getName(), responseDTO.name());
        assertEquals(preSavedTestOffice.getAddress(), responseDTO.address());
    }

    @Test
    void getPostOfficeByPostcode_whenPostcodeIsNotValid_shouldReturnBadRequest() throws Exception {
        String jsonResponse = mockMvc.perform(get(
                        "/api/v1/post-offices/{postcode}", "000000a"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorResponseDTO responseDTO = objectMapper.readValue(jsonResponse, ErrorResponseDTO.class);

        assertNotNull(responseDTO);
        assertNotNull(responseDTO.uri());
        assertNotNull(responseDTO.status());
        assertNotNull(responseDTO.message());
        assertNotNull(responseDTO.timestamp());
    }

    @Test
    void getPostOfficeByPostcode_whenPostcodeIsNotExists_shouldReturnNotFound() throws Exception {
        String jsonResponse = mockMvc.perform(get(
                        "/api/v1/post-offices/{postcode}", "000000"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorResponseDTO responseDTO = objectMapper.readValue(jsonResponse, ErrorResponseDTO.class);

        assertNotNull(responseDTO);
        assertNotNull(responseDTO.uri());
        assertNotNull(responseDTO.status());
        assertNotNull(responseDTO.message());
        assertNotNull(responseDTO.timestamp());
    }

    @Test
    void getPostOfficesList_whenDatabaseHasPostOffice_shouldReturnListWithPostOffice() throws Exception {
        String jsonResponse = mockMvc.perform(get("/api/v1/post-offices"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<PostOfficeResponseDTO> responseDTO = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });

        assertNotNull(responseDTO);
        assertNotNull(responseDTO.get(0));
        assertNotNull(responseDTO.get(0).id());
        assertEquals(preSavedTestOffice.getPostcode(), responseDTO.get(0).postcode());
        assertEquals(preSavedTestOffice.getName(), responseDTO.get(0).name());
        assertEquals(preSavedTestOffice.getAddress(), responseDTO.get(0).address());
    }

    @Test
    void getPostOfficesList_whenDatabaseEmpty_shouldReturnEmptyList() throws Exception {
        postOfficeRepository.deleteAll();

        String jsonResponse = mockMvc.perform(get("/api/v1/post-offices"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").doesNotExist())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<PostOfficeResponseDTO> responseDTO = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });

        assertNotNull(responseDTO);
        assertTrue(responseDTO.isEmpty());
    }


}
