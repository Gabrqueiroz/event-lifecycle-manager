package com.gabrielqueiroz.event_lifecycle_manager.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventRequestDto {

        @NotBlank(message = "Nome é obrigatório")
        private String name;

        @NotNull(message = "Data de início é obrigatória")
        private LocalDate startDate;

        @NotNull(message = "Data de fim é obrigatória")
        private LocalDate endDate;

        @NotNull(message = "ID da instituição é obrigatório")
        private Long institutionId;
}

