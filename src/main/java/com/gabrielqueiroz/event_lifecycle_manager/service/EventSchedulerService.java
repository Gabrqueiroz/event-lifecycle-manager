package com.gabrielqueiroz.event_lifecycle_manager.service;

import com.gabrielqueiroz.event_lifecycle_manager.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventSchedulerService {

    private final EventRepository eventRepository;

    @Scheduled(cron = "0 */1 * * * *")
    @Transactional
    public void deactivateExpiredEvents() {
        LocalDate today = LocalDate.now();

        log.info("⏰ [{}] Analisando eventos expirados...", today);

        Integer updated = eventRepository.deactivateAllExpiredEvents(today);

        if (updated > 0) {
            log.info("✅ {} evento(s) desativado(s) em {}", updated, today);
        } else {
            log.info("📭 Nenhum evento expirado encontrado em {}", today);  // ✅ ADICIONE ISSO!
        }
    }
}