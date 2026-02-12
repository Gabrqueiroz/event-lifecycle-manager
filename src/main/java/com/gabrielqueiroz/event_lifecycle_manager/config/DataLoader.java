package com.gabrielqueiroz.event_lifecycle_manager.config;

import com.gabrielqueiroz.event_lifecycle_manager.model.EventModel;
import com.gabrielqueiroz.event_lifecycle_manager.model.InstitutionModel;
import com.gabrielqueiroz.event_lifecycle_manager.repository.EventRepository;
import com.gabrielqueiroz.event_lifecycle_manager.repository.InstitutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final InstitutionRepository institutionRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public void run(String... args) {

        if (institutionRepository.count() == 0) {
            log.info("📀 Carregando dados iniciais...");

            // =========================================
            // 1. CRIAR 5 INSTITUIÇÕES FAKE
            // =========================================
            List<InstitutionModel> institutions = Arrays.asList(
                    InstitutionModel.builder()
                            .name("Cooperativa Central Aurora")
                            .type("Cooperativa de Crédito")
                            .build(),
                    InstitutionModel.builder()
                            .name("Instituto Horizonte")
                            .type("Educação")
                            .build(),
                    InstitutionModel.builder()
                            .name("Fundação Serra Verde")
                            .type("Saúde")
                            .build(),
                    InstitutionModel.builder()
                            .name("Associação Cultural Monte Azul")
                            .type("Cultura")
                            .build(),
                    InstitutionModel.builder()
                            .name("Centro de Inovação Sul")
                            .type("Tecnologia")
                            .build()
            );

            List<InstitutionModel> savedInstitutions = institutionRepository.saveAll(institutions);
            log.info("✅ {} instituições criadas", savedInstitutions.size());

            // =========================================
            // 2. CRIAR DIVERSOS EVENTOS PARA CADA INSTITUIÇÃO
            // =========================================

            // Instituição 1 - Cooperativa Central Aurora
            createEventsForInstitution(savedInstitutions.get(0), Arrays.asList(
                    EventModel.builder()
                            .name("Workshop de Educação Financeira")
                            .startDate(LocalDate.now().plusDays(5))
                            .endDate(LocalDate.now().plusDays(7))
                            .active(false)
                            .build(),
                    EventModel.builder()
                            .name("Palestra: Investimentos Seguros")
                            .startDate(LocalDate.now().plusMonths(1))
                            .endDate(LocalDate.now().plusMonths(1).plusDays(1))
                            .active(false)
                            .build(),
                    EventModel.builder()
                            .name("Curso de Cooperativismo")
                            .startDate(LocalDate.now().minusMonths(2))
                            .endDate(LocalDate.now().minusMonths(2).plusDays(3))
                            .active(false)  // ✅ Expirado
                            .build(),
                    EventModel.builder()
                            .name("Feira de Serviços Financeiros")
                            .startDate(LocalDate.now().plusWeeks(3))
                            .endDate(LocalDate.now().plusWeeks(4))
                            .active(false)
                            .build()
            ));

            // Instituição 2 - Instituto Horizonte
            createEventsForInstitution(savedInstitutions.get(1), Arrays.asList(
                    EventModel.builder()
                            .name("Semana da Educação")
                            .startDate(LocalDate.now().plusDays(10))
                            .endDate(LocalDate.now().plusDays(15))
                            .active(false)
                            .build(),
                    EventModel.builder()
                            .name("Curso de Capacitação Docente")
                            .startDate(LocalDate.now().minusMonths(1))
                            .endDate(LocalDate.now().minusMonths(1).plusDays(5))
                            .active(false)  // ✅ Expirado
                            .build(),
                    EventModel.builder()
                            .name("Fórum de Inovação Educacional")
                            .startDate(LocalDate.now().plusMonths(2))
                            .endDate(LocalDate.now().plusMonths(2).plusDays(2))
                            .active(false)
                            .build(),
                    EventModel.builder()
                            .name("Oficina de Metodologias Ativas")
                            .startDate(LocalDate.now().plusDays(20))
                            .endDate(LocalDate.now().plusDays(22))
                            .active(false)
                            .build()
            ));

            // Instituição 3 - Fundação Serra Verde
            createEventsForInstitution(savedInstitutions.get(2), Arrays.asList(
                    EventModel.builder()
                            .name("Campanha de Vacinação")
                            .startDate(LocalDate.now().plusDays(2))
                            .endDate(LocalDate.now().plusDays(5))
                            .active(false)
                            .build(),
                    EventModel.builder()
                            .name("Palestra: Saúde Preventiva")
                            .startDate(LocalDate.now().plusWeeks(2))
                            .endDate(LocalDate.now().plusWeeks(2).plusDays(1))
                            .active(false)
                            .build(),
                    EventModel.builder()
                            .name("Workshop de Nutrição")
                            .startDate(LocalDate.now().minusWeeks(3))
                            .endDate(LocalDate.now().minusWeeks(3).plusDays(2))
                            .active(false)  // ✅ Expirado
                            .build(),
                    EventModel.builder()
                            .name("Mutirão de Exames")
                            .startDate(LocalDate.now().plusDays(15))
                            .endDate(LocalDate.now().plusDays(16))
                            .active(false)
                            .build(),
                    EventModel.builder()
                            .name("Jornada de Bem-Estar")
                            .startDate(LocalDate.now().plusMonths(1).plusDays(10))
                            .endDate(LocalDate.now().plusMonths(1).plusDays(12))
                            .active(false)
                            .build()
            ));

            // Instituição 4 - Associação Cultural Monte Azul
            createEventsForInstitution(savedInstitutions.get(3), Arrays.asList(
                    EventModel.builder()
                            .name("Festival de Música")
                            .startDate(LocalDate.now().plusDays(25))
                            .endDate(LocalDate.now().plusDays(28))
                            .active(false)
                            .build(),
                    EventModel.builder()
                            .name("Exposição de Arte Contemporânea")
                            .startDate(LocalDate.now().plusWeeks(4))
                            .endDate(LocalDate.now().plusWeeks(5))
                            .active(false)
                            .build(),
                    EventModel.builder()
                            .name("Oficina de Teatro")
                            .startDate(LocalDate.now().minusMonths(1).plusDays(5))
                            .endDate(LocalDate.now().minusMonths(1).plusDays(10))
                            .active(false)  // ✅ Expirado
                            .build(),
                    EventModel.builder()
                            .name("Cineclube: Mostra de Cinema Nacional")
                            .startDate(LocalDate.now().plusDays(7))
                            .endDate(LocalDate.now().plusDays(9))
                            .active(false)
                            .build(),
                    EventModel.builder()
                            .name("Feira de Artesanato")
                            .startDate(LocalDate.now().plusMonths(1))
                            .endDate(LocalDate.now().plusMonths(1).plusDays(3))
                            .active(false)
                            .build()
            ));

            // Instituição 5 - Centro de Inovação Sul
            createEventsForInstitution(savedInstitutions.get(4), Arrays.asList(
                    EventModel.builder()
                            .name("Hackathon de Tecnologia")
                            .startDate(LocalDate.now().plusDays(30))
                            .endDate(LocalDate.now().plusDays(32))
                            .active(false)
                            .build(),
                    EventModel.builder()
                            .name("Workshop de Inteligência Artificial")
                            .startDate(LocalDate.now().plusWeeks(2))
                            .endDate(LocalDate.now().plusWeeks(2).plusDays(2))
                            .active(false)
                            .build(),
                    EventModel.builder()
                            .name("Palestra: Carreira em Tech")
                            .startDate(LocalDate.now().minusWeeks(4))
                            .endDate(LocalDate.now().minusWeeks(4).plusDays(1))
                            .active(false)  // ✅ Expirado
                            .build(),
                    EventModel.builder()
                            .name("Curso de Desenvolvimento Web")
                            .startDate(LocalDate.now().plusMonths(2))
                            .endDate(LocalDate.now().plusMonths(2).plusDays(20))
                            .active(false)
                            .build(),
                    EventModel.builder()
                            .name("Meetup de Cloud Computing")
                            .startDate(LocalDate.now().plusDays(12))
                            .endDate(LocalDate.now().plusDays(12))
                            .active(false)
                            .build(),
                    EventModel.builder()
                            .name("Conferência de Inovação")
                            .startDate(LocalDate.now().plusMonths(3))
                            .endDate(LocalDate.now().plusMonths(3).plusDays(2))
                            .active(false)
                            .build()
            ));

            // =========================================
            // 3. CRIAR ALGUNS EVENTOS COM DATA = HOJE (ATIVOS)
            // =========================================
            EventModel eventoHoje1 = EventModel.builder()
                    .name("Workshop: Introdução ao Cooperativismo")
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now().plusDays(2))
                    .institutionModel(savedInstitutions.get(0))
                    .active(true)  // ✅ Ativo (data = hoje)
                    .build();

            EventModel eventoHoje2 = EventModel.builder()
                    .name("Roda de Conversa: Saúde Mental")
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now().plusDays(1))
                    .institutionModel(savedInstitutions.get(2))
                    .active(true)  // ✅ Ativo (data = hoje)
                    .build();

            EventModel eventoHoje3 = EventModel.builder()
                    .name("Oficina de Programação para Iniciantes")
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now().plusDays(3))
                    .institutionModel(savedInstitutions.get(4))
                    .active(true)  // ✅ Ativo (data = hoje)
                    .build();

            eventRepository.saveAll(Arrays.asList(eventoHoje1, eventoHoje2, eventoHoje3));

            // =========================================
            // 4. RESUMO DOS DADOS CARREGADOS
            // =========================================
            long totalInstitutions = institutionRepository.count();
            long totalEvents = eventRepository.count();

            log.info("========================================");
            log.info("📊 RESUMO DO CARREGAMENTO:");
            log.info("✅ {} instituições criadas", totalInstitutions);
            log.info("✅ {} eventos criados", totalEvents);
            log.info("📅 Data atual: {}", LocalDate.now());
            log.info("🎯 Eventos ativos hoje: 3");
            log.info("========================================");

        } else {
            log.info("📁 Banco de dados já contém {} instituições e {} eventos",
                    institutionRepository.count(), eventRepository.count());
        }
    }

    @Transactional
    public void createEventsForInstitution(InstitutionModel institution, List<EventModel> events) {
        events.forEach(event -> event.setInstitutionModel(institution));
        eventRepository.saveAll(events);
        log.debug("   ✅ {} eventos criados para {}", events.size(), institution.getName());
    }
}
