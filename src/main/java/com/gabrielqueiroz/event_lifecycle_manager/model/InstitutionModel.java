package com.gabrielqueiroz.event_lifecycle_manager.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "sb_institution")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstitutionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @OneToMany(mappedBy = "institutionModel")  // ✅ AGORA CORRESPONDE AO CAMPO ABAIXO
    private List<EventModel> events;            // ✅ List<EventModel> (não Event)
}