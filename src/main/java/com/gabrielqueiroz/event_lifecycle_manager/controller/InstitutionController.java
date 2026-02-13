package com.gabrielqueiroz.event_lifecycle_manager.controller;

import com.gabrielqueiroz.event_lifecycle_manager.dto.request.InstitutionRequestDto;
import com.gabrielqueiroz.event_lifecycle_manager.dto.response.InstitutionResponse;
import com.gabrielqueiroz.event_lifecycle_manager.service.InstitutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/institutions")
@RequiredArgsConstructor
@Tag(name = "Instituições", description = "CRUD de instituições")
public class InstitutionController {
    private final InstitutionService institutionService;
    @Operation(summary = "Criar nova instituição")
    @PostMapping("/institution")
    public ResponseEntity<InstitutionResponse> create(@Valid @RequestBody InstitutionRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(institutionService.create(request));
    }

    @Operation(summary = "Buscar instituição por ID com seus eventos")
    @GetMapping("/institution/{id}/events")
    public ResponseEntity<InstitutionResponse> findByIdWithEvents(@PathVariable Long id) {
        return ResponseEntity.ok(institutionService.findByIdWithEvents(id));
    }

    @Operation(summary = "Listar todas instituições com seus eventos")
    @GetMapping("/institutions/events")
    public ResponseEntity<List<InstitutionResponse>> findAllWithEvents() {
        return ResponseEntity.ok(institutionService.findAllWithEvents());
    }
}
