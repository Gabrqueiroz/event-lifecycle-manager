package com.gabrielqueiroz.event_lifecycle_manager.repository;
import com.gabrielqueiroz.event_lifecycle_manager.model.InstitutionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<InstitutionModel, Long> {
}
