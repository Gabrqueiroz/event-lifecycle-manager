package com.gabrielqueiroz.event_lifecycle_manager.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstitutionRequestDto {
    @NotBlank(message = "Institution name is required")
    private String name;

    @NotBlank(message = "Institution type is required")
    private String type;
}
