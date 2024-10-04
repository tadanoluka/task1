package ru.tadanoluka.task1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.tadanoluka.task1.configuration.TestcontainersConfiguration;
import ru.tadanoluka.task1.controller.PostOfficeController;
import ru.tadanoluka.task1.controller.PostalItemController;
import ru.tadanoluka.task1.controller.PostalMovementController;
import ru.tadanoluka.task1.mapper.PostOfficeMapper;
import ru.tadanoluka.task1.mapper.PostalItemMapper;
import ru.tadanoluka.task1.mapper.PostalMovementMapper;
import ru.tadanoluka.task1.repository.PostOfficeRepository;
import ru.tadanoluka.task1.repository.PostalItemRepository;
import ru.tadanoluka.task1.repository.PostalMovementRepository;
import ru.tadanoluka.task1.service.PostOfficeService;
import ru.tadanoluka.task1.service.PostalItemService;
import ru.tadanoluka.task1.service.PostalMovementService;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class SmokeTest {

    @Autowired
    PostalItemController postalItemController;
    @Autowired
    PostalMovementController postalMovementController;
    @Autowired
    PostOfficeController postOfficeController;

    @Autowired
    PostalItemService postalItemService;
    @Autowired
    PostalMovementService postalMovementService;
    @Autowired
    PostOfficeService postOfficeService;

    @Autowired
    PostalItemMapper postalItemMapper;
    @Autowired
    PostalMovementMapper postalMovementMapper;
    @Autowired
    PostOfficeMapper postOfficeMapper;

    @Autowired
    PostalItemRepository postalItemRepository;
    @Autowired
    PostalMovementRepository postalMovementRepository;
    @Autowired
    PostOfficeRepository postOfficeRepository;

    @Test
    void contextLoads() {
        assertThat(postalItemController).isNotNull();
        assertThat(postalMovementController).isNotNull();
        assertThat(postOfficeController).isNotNull();

        assertThat(postalItemService).isNotNull();
        assertThat(postalMovementService).isNotNull();
        assertThat(postOfficeService).isNotNull();

        assertThat(postalItemMapper).isNotNull();
        assertThat(postalMovementMapper).isNotNull();
        assertThat(postOfficeMapper).isNotNull();

        assertThat(postalItemRepository).isNotNull();
        assertThat(postalMovementRepository).isNotNull();
        assertThat(postOfficeRepository).isNotNull();
    }

}
