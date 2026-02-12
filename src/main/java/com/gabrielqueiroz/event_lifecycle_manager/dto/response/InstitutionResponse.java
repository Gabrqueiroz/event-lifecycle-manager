package com.gabrielqueiroz.event_lifecycle_manager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstitutionResponse{
    private Long id;
    private String name;
    private String type;
    private List<EventResponse> events;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
