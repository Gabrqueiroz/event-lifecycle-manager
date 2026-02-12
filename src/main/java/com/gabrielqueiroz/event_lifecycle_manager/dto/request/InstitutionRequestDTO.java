package com.gabrielqueiroz.event_lifecycle_manager.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class InstitutionRequestDTO {
    @NotBlank(message = "Institution name is required")
    private String name;

    @NotBlank(message = "Institution type is required")
    private String type;
}
