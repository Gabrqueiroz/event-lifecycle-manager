package com.gabrielqueiroz.event_lifecycle_manager.model;


import jakarta.persistence.*;
import jdk.jfr.Event;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "sb_institution")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @OneToMany(mappedBy = "institution")
    private List<Event> events;
}
