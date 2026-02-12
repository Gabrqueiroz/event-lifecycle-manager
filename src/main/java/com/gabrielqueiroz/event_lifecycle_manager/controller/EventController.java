package com.gabrielqueiroz.event_lifecycle_manager.controller;


import com.gabrielqueiroz.event_lifecycle_manager.dto.request.EventRequestDto;
import com.gabrielqueiroz.event_lifecycle_manager.dto.response.EventResponse;
import com.gabrielqueiroz.event_lifecycle_manager.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
@Tag(name = "Eventos", description = "API completa para gerenciamento de eventos")
public class EventController {

    private final EventService eventService;

    @Operation(summary = "Cria uma novo evento", description = "Criar evento.")
    @PostMapping("/event")
    public ResponseEntity<EventResponse> create(@Valid @RequestBody EventRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.create(request));
    }
}
