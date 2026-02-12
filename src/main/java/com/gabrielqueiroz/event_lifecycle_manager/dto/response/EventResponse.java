package com.gabrielqueiroz.event_lifecycle_manager.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EventResponse {
    private Long id;

    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
