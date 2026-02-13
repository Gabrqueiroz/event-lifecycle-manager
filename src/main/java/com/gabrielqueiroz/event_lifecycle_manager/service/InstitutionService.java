package com.gabrielqueiroz.event_lifecycle_manager.service;
import com.gabrielqueiroz.event_lifecycle_manager.dto.request.InstitutionRequestDto;
import com.gabrielqueiroz.event_lifecycle_manager.dto.response.EventResponse;
import com.gabrielqueiroz.event_lifecycle_manager.dto.response.InstitutionResponse;
import com.gabrielqueiroz.event_lifecycle_manager.model.EventModel;
import com.gabrielqueiroz.event_lifecycle_manager.model.InstitutionModel;
import com.gabrielqueiroz.event_lifecycle_manager.repository.EventRepository;
import com.gabrielqueiroz.event_lifecycle_manager.repository.InstitutionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Slf4j
public class InstitutionService {

    private final EventRepository eventRepository;
    private final InstitutionRepository institutionRepository;

    @Transactional
    public InstitutionResponse create(InstitutionRequestDto dto) {

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
                .createdAt(LocalDate.now())
                .updatedAt(savedInstitution.getUpdatedAt())
                .build();
    }


    // =========================================
    // FIND BY ID COM EVENTOS
    // =========================================
    @Transactional(readOnly = true)
    public InstitutionResponse findByIdWithEvents(Long id) {
        log.info("Buscando instituição ID: {} com seus eventos", id);

        InstitutionModel institution = institutionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instituição não encontrada com ID: " + id));

        // Buscar eventos desta instituição
        List<EventModel> events = eventRepository.findByInstitutionId(id);

        // Converter eventos para EventResponse (COMPLETO)
        List<EventResponse> eventResponses = events.stream()
                .map(this::buildEventResponse)
                .collect(Collectors.toList());

        log.info("✅ Instituição ID: {} encontrada com {} eventos", id, eventResponses.size());

        return InstitutionResponse.builder()
                .id(institution.getId())
                .name(institution.getName())
                .type(institution.getType())
                .events(eventResponses)  // ✅ LISTA COMPLETA DE EVENTOS
                .createdAt(institution.getCreatedAt() != null ?
                        institution.getCreatedAt().toLocalDate() : LocalDate.now())
                .updatedAt(institution.getUpdatedAt())
                .build();
    }

    // =========================================
    // FIND ALL COM EVENTOS
    // =========================================
    @Transactional(readOnly = true)
    public List<InstitutionResponse> findAllWithEvents() {
        log.info("Buscando todas as instituições com seus eventos");

        return institutionRepository.findAll().stream()
                .map(institution -> {
                    List<EventModel> events = eventRepository.findByInstitutionId(institution.getId());
                    List<EventResponse> eventResponses = events.stream()
                            .map(this::buildEventResponse)
                            .collect(Collectors.toList());

                    return InstitutionResponse.builder()
                            .id(institution.getId())
                            .name(institution.getName())
                            .type(institution.getType())
                            .events(eventResponses)
                            .createdAt(institution.getCreatedAt() != null ?
                                    institution.getCreatedAt().toLocalDate() : LocalDate.now())
                            .updatedAt(institution.getUpdatedAt())
                            .build();
                })
                .collect(Collectors.toList());
    }

    // =========================================
    // PRIVATE METHODS
    // =========================================
    private EventResponse buildEventResponse(EventModel event) {
        return EventResponse.builder()
                .id(event.getId())
                .name(event.getName())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .active(event.getActive())
                .institutionId(event.getInstitutionModel().getId())
                .institutionName(event.getInstitutionModel().getName())
                .build();
    }
}
