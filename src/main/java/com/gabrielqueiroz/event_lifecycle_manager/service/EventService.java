package com.gabrielqueiroz.event_lifecycle_manager.service;

import com.gabrielqueiroz.event_lifecycle_manager.dto.request.EventRequestDto;
import com.gabrielqueiroz.event_lifecycle_manager.dto.response.EventResponse;
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
public class EventService {

    private final EventRepository eventRepository;
    private final InstitutionRepository institutionRepository;

    @Transactional
    public EventResponse create(EventRequestDto dto) {
        try {
            InstitutionModel institution = findInstitutionById(dto.getInstitutionId());

            EventModel event = EventModel.builder()
                    .name(dto.getName())
                    .startDate(dto.getStartDate())
                    .endDate(dto.getEndDate())
                    .institutionModel(institution)
                    .active(false)
                    .build();

            if (dto.getStartDate().equals(LocalDate.now())) {
                event.setActive(true);
            }

            EventModel savedEvent = eventRepository.save(event);

            return EventResponse.builder()
                    .id(savedEvent.getId())
                    .name(savedEvent.getName())
                    .startDate(savedEvent.getStartDate())
                    .endDate(savedEvent.getEndDate())
                    .active(savedEvent.getActive())
                    .institutionId(savedEvent.getInstitutionModel().getId())
                    .institutionName(savedEvent.getInstitutionModel().getName())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
        } catch (EntityNotFoundException e) {
            log.error("❌ Instituição não encontrada: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("❌ Erro ao criar evento: {}", e.getMessage());
            throw new RuntimeException("Erro ao criar evento: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public EventResponse findById(Long id) {
        try {
            EventModel event = findEventById(id);
            return buildResponse(event);
        } catch (EntityNotFoundException e) {
            log.error("❌ Evento não encontrado: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<EventResponse> findAll() {
        try {
            return eventRepository.findAll()
                    .stream()
                    .map(this::buildResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("❌ Erro ao buscar eventos: {}", e.getMessage());
            throw new RuntimeException("Erro ao buscar eventos");
        }
    }

    @Transactional(readOnly = true)
    public List<EventResponse> findByActive(Boolean active) {
        try {
            return eventRepository.findByActive(active)
                    .stream()
                    .map(this::buildResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("❌ Erro ao buscar eventos por status: {}", e.getMessage());
            throw new RuntimeException("Erro ao buscar eventos por status");
        }
    }

    @Transactional(readOnly = true)
    public List<EventResponse> findByInstitutionId(Long institutionId) {
        try {
            return eventRepository.findByInstitutionId(institutionId)
                    .stream()
                    .map(this::buildResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Erro ao buscar eventos por instituição: {}", e.getMessage());
            throw new RuntimeException("Erro ao buscar eventos por instituição");
        }
    }

    @Transactional
    public EventResponse update(Long id, EventRequestDto dto) {
        try {
            EventModel event = findEventById(id);
            InstitutionModel institution = findInstitutionById(dto.getInstitutionId());

            event.setName(dto.getName());
            event.setStartDate(dto.getStartDate());
            event.setEndDate(dto.getEndDate());
            event.setInstitutionModel(institution);

            // Reavalia o status baseado nas novas datas
            LocalDate today = LocalDate.now();

            if (dto.getStartDate().equals(today)) {
                event.setActive(true);
                log.info("Evento ID: {} ativado (startDate = hoje)", id);
            } else if (dto.getEndDate().isBefore(today)) {
                event.setActive(false);
                log.info("Evento ID: {} desativado (endDate no passado)", id);
            } else if (dto.getStartDate().isAfter(today)) {
                event.setActive(false);
                log.info("⏳ Evento ID: {} pendente (startDate futuro)", id);
            } else {
                event.setActive(true);
                log.info("Evento ID: {} ativo (dentro do período)", id);
            }

            EventModel updatedEvent = eventRepository.save(event);

            return buildResponse(updatedEvent);
        } catch (EntityNotFoundException e) {
            log.error("Erro ao atualizar evento: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Erro ao atualizar evento ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Erro ao atualizar evento");
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            if (!eventRepository.existsById(id)) {
                throw new EntityNotFoundException("Evento não encontrado com ID: " + id);
            }
            eventRepository.deleteById(id);
            log.info("Evento ID: {} deletado com sucesso", id);
        } catch (EntityNotFoundException e) {
            log.error("Erro ao deletar evento: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Erro ao deletar evento ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Erro ao deletar evento");
        }
    }

    private InstitutionModel findInstitutionById(Long id) {
        return institutionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instituição não encontrada com ID: " + id));
    }

    private EventModel findEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento não encontrado com ID: " + id));
    }

    private EventResponse buildResponse(EventModel event) {
        return EventResponse.builder()
                .id(event.getId())
                .name(event.getName())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .active(event.getActive())
                .institutionId(event.getInstitutionModel().getId())
                .institutionName(event.getInstitutionModel().getName())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}

