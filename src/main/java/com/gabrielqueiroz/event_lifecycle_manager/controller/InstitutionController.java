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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
