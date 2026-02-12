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
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private InstitutionModel findInstitutionById(Long id) {
        return institutionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instituição não encontrada com ID: " + id));
    }
}
