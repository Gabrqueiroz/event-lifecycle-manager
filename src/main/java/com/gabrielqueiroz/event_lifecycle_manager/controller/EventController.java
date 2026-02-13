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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "Busca evento por ID", description = "Retorna um evento específico.")
    @GetMapping("/event/{id}")
    public ResponseEntity<EventResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.findById(id));
    }

    @Operation(summary = "Lista todos os eventos", description = "Retorna todos os eventos.")
    @GetMapping("/events")
    public ResponseEntity<List<EventResponse>> findAll() {
        return ResponseEntity.ok(eventService.findAll());
    }

    @Operation(summary = "Lista eventos por status", description = "Filtra eventos por ativo/inativo.")
    @GetMapping("/events/status/{active}")
    public ResponseEntity<List<EventResponse>> findByActive(@PathVariable Boolean active) {
        return ResponseEntity.ok(eventService.findByActive(active));
    }

    @Operation(summary = "Lista eventos por instituição", description = "Filtra eventos por instituição.")
    @GetMapping("/events/institution/{institutionId}")
    public ResponseEntity<List<EventResponse>> findByInstitutionId(@PathVariable Long institutionId) {
        return ResponseEntity.ok(eventService.findByInstitutionId(institutionId));
    }

    @Operation(summary = "Atualiza um evento", description = "Atualiza dados de um evento existente.")
    @PutMapping("/event/{id}")
    public ResponseEntity<EventResponse> update(@PathVariable Long id, @Valid @RequestBody EventRequestDto request) {
        return ResponseEntity.ok(eventService.update(id, request));
    }

    @Operation(summary = "Deleta um evento", description = "Remove um evento do sistema.")
    @DeleteMapping("/event/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
