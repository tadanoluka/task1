package ru.tadanoluka.task1.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import ru.tadanoluka.task1.configuration.TestcontainersConfiguration;
import ru.tadanoluka.task1.model.PostOffice;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class PostOfficeRepositoryIntegrationTest {

    @Autowired
    PostOfficeRepository postOfficeRepository;
    @Autowired
    EntityManager entityManager;

    PostOffice preSavedPostOffice = PostOffice.builder()
            .postcode("123451")
            .name("Test name 1")
            .address("Test address")
            .build();

    @BeforeEach
    void setUp() {
        postOfficeRepository.save(preSavedPostOffice);
    }

    @AfterEach
    void tearDown() {
        postOfficeRepository.deleteAll();
    }

    @Test
    void save_whenValidEntity_shouldSavePostOfficeToDB() {
        PostOffice postOffice = PostOffice.builder()
                .postcode("123456")
                .address("Test1")
                .name("Test1")
                .build();

        PostOffice savedPostOffice = postOfficeRepository.save(postOffice);
        assertNotNull(savedPostOffice);
    }

    @Test
    void save_whenPostCodeInvalid_shouldNotSavePostOfficeToDB() {
        PostOffice postOffice = PostOffice.builder()
                .postcode(preSavedPostOffice.getPostcode())
                .address("Test1")
                .name("Test1")
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> postOfficeRepository.save(postOffice));
        assertFalse(entityManager.contains(postOffice));
    }

    @Test
    void save_whenPostCodeConflicts_shouldNotSavePostOfficeToDB() {
        PostOffice postOffice = PostOffice.builder()
                .postcode("1234567")
                .address("Test1")
                .name("Test1")
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> postOfficeRepository.save(postOffice));
        assertFalse(entityManager.contains(postOffice));
    }

    @Test
    void findByPostcode_whenPostOfficeExists_shouldReturnPostOffice() {
        Optional<PostOffice> officeOptional = postOfficeRepository.findByPostcode(preSavedPostOffice.getPostcode());

        assertNotNull(officeOptional);
        assertTrue(officeOptional.isPresent());
        assertEquals(preSavedPostOffice.getPostcode(), officeOptional.get().getPostcode());
    }

    @Test
    void findByPostcode_whenPostOfficeDoesNotExist_shouldReturnEmptyOptional() {
        Optional<PostOffice> officeOptional = postOfficeRepository.findByPostcode("1234567");

        assertNotNull(officeOptional);
        assertTrue(officeOptional.isEmpty());
    }

    @Test
    void existsByPostcode_whenPostOfficeExists_shouldReturnTrue() {
        boolean response = postOfficeRepository.existsByPostcode(preSavedPostOffice.getPostcode());

        assertTrue(response);
    }

    @Test
    void existsByPostcode_whenPostOfficeDoesNotExist_shouldReturnFalse() {
        boolean response = postOfficeRepository.existsByPostcode("1234567");

        assertFalse(response);
    }
}