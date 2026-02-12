package com.gabrielqueiroz.event_lifecycle_manager.dto.request;

import jakarta.validation.constraints.NotBlank;

public class InstitutionRequestDto {
    @NotBlank(message = "Institution name is required")
    private String name;

    @NotBlank(message = "Institution type is required")
    private String type;
}
