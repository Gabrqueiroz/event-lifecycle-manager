package com.gabrielqueiroz.event_lifecycle_manager.repository;

import com.gabrielqueiroz.event_lifecycle_manager.model.EventModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<EventModel, Long> {
    List<EventModel> findByActive(Boolean active);

    @Query("SELECT e FROM EventModel e WHERE e.startDate <= :today AND e.endDate >= :today AND e.active = false")
    List<EventModel> findEventsToActivate(@Param("today") LocalDate today);

    @Query("SELECT e FROM EventModel e WHERE e.endDate < :today AND e.active = true")
    List<EventModel> findEventsToDeactivate(@Param("today") LocalDate today);

    @Query("SELECT e FROM EventModel e WHERE e.institutionModel.id = :institutionId")
    List<EventModel> findByInstitutionId(@Param("institutionId") Long institutionId);

    @Modifying
    @Transactional
    @Query("UPDATE EventModel e SET e.active = false WHERE e.endDate < :today AND e.active = true")
    int deactivateAllExpiredEvents(@Param("today") LocalDate today);
}
