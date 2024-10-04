package ru.tadanoluka.task1.service.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tadanoluka.task1.controller.dto.request.CreatePostOfficeRequestDTO;
import ru.tadanoluka.task1.controller.dto.response.PostOfficeResponseDTO;
import ru.tadanoluka.task1.controller.exception.exceptions.NotFoundException;
import ru.tadanoluka.task1.controller.exception.exceptions.UniqueConstraintViolationException;
import ru.tadanoluka.task1.mapper.PostOfficeMapper;
import ru.tadanoluka.task1.model.PostOffice;
import ru.tadanoluka.task1.repository.PostOfficeRepository;
import ru.tadanoluka.task1.service.PostOfficeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostOfficeServiceImpl implements PostOfficeService {

    private final PostOfficeRepository postOfficeRepository;
    private final PostOfficeMapper postOfficeMapper;

    @Transactional
    @Override
    public PostOfficeResponseDTO createPostOffice(CreatePostOfficeRequestDTO postOfficeDTO) {
        PostOffice postOfficeFromDTO = postOfficeMapper.toPostOfficeFromDTO(postOfficeDTO);

        if (postOfficeRepository.existsByPostcode(postOfficeDTO.postcode())) {
            throw new UniqueConstraintViolationException(
                    "The postcode '%s' already exists".formatted(postOfficeDTO.postcode())
            );
        }

        PostOffice savedPostOffice = postOfficeRepository.save(postOfficeFromDTO);
        return postOfficeMapper.toPostOfficeResponseDTO(savedPostOffice);
    }

    @Override
    public List<PostOfficeResponseDTO> getPostOfficesDTOList() {
        return postOfficeRepository.findAll().stream()
                .map(postOfficeMapper::toPostOfficeResponseDTO)
                .toList();
    }

    @Override
    public PostOfficeResponseDTO getPostOfficeDTOByPostcode(String postcode) {
        return postOfficeMapper.toPostOfficeResponseDTO(findPostOfficeByPostcode(postcode));
    }

    @Override
    public PostOffice findPostOfficeByPostcode(String postcode) {
        return postOfficeRepository.findByPostcode(postcode)
                .orElseThrow(() -> new NotFoundException(
                        "The post office with postcode '%s' does not exists".formatted(postcode)
                ));
    }
}
