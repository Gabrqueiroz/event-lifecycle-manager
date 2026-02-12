package com.gabrielqueiroz.event_lifecycle_manager.service;
import com.gabrielqueiroz.event_lifecycle_manager.dto.request.InstitutionRequestDto;
import com.gabrielqueiroz.event_lifecycle_manager.dto.response.InstitutionResponse;
import com.gabrielqueiroz.event_lifecycle_manager.model.InstitutionModel;
import com.gabrielqueiroz.event_lifecycle_manager.repository.InstitutionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Slf4j
public class InstitutionService {

    private final InstitutionRepository institutionRepository;

    @Transactional
    public InstitutionResponse create(InstitutionRequestDto dto) {
        log.info("Criando nova instituição: {}", dto.getName());

        InstitutionModel institution = InstitutionModel.builder()
                .name(dto.getName())
                .type(dto.getType())
                .build();

        InstitutionModel savedInstitution = institutionRepository.save(institution);
        log.info("Instituição criada com ID: {}", savedInstitution.getId());

        return InstitutionResponse.builder()
                .id(savedInstitution.getId())
                .name(savedInstitution.getName())
                .type(savedInstitution.getType())
                .createdAt(savedInstitution.getCreatedAt())
                .updatedAt(savedInstitution.getUpdatedAt())
                .build();
    }
}
