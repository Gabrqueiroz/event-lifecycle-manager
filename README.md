# event-lifecycle-manager
🚀 Sobre o Projeto
API REST para gerenciamento de eventos com ativação e desativação automática. Desenvolvida como solução completa para controle de ciclo de vida de eventos, seguindo boas práticas de desenvolvimento e arquitetura limpa.

🎯 Funcionalidades
✅ CRUD completo de Instituições

✅ CRUD completo de Eventos

✅ Validação de datas (fim > início)

✅ Ativação automática (quando startDate = hoje)

✅ Desativação automática (eventos expirados)

✅ Verificação periódica a cada 5 minutos

✅ Documentação Swagger integrada

✅ Banco H2 para testes

✅ Tratamento global de exceções

✅ DataLoader com dados fictícios para testes

⏰ SISTEMA DE AGENDAMENTO AUTOMÁTICO
Como funciona o ciclo de vida do evento?
java
@Scheduled(cron = "0 */5 * * * *")  // A CADA 5 MINUTOS
public void checkExpiredEvents() {
    // 1. Busca eventos com endDate < hoje E active = true
    // 2. Marca todos como active = false
    // 3. Registra log com quantidade desativada
}
Regras de negócio implementadas:
Regra	Quando	Ação
✅ Ativação imediata	startDate = hoje	active = true
✅ Ativação programada	startDate ≤ hoje ≤ endDate	active = true (scheduler)
✅ Desativação automática	endDate < hoje	active = false (a cada 5min)
✅ Validação de período	endDate < startDate	❌ Erro 400
🛠️ TECNOLOGIAS UTILIZADAS
Tecnologia	Versão	Finalidade
Java	17	Linguagem de programação
Spring Boot	3.2.4	Framework principal
Spring Data JPA	-	Persistência e ORM
Spring Validation	-	Validações de dados
H2 Database	-	Banco em memória para testes
Lombok	-	Redução de código boilerplate
Swagger/OpenAPI	2.5.0	Documentação interativa
Maven	-	Gerenciamento de dependências
📁 ESTRUTURA DO PROJETO
text
src/main/java/com/gabrielqueiroz/event_lifecycle_manager/
├── config/
│   └── SchedulerConfig.java           # Habilita agendamento
│   └── DataLoader.java               # Dados iniciais para testes
├── controller/
│   ├── EventController.java           # Endpoints de eventos
│   └── InstitutionController.java     # Endpoints de instituições
├── dto/
│   ├── request/
│   │   ├── EventRequestDto.java
│   │   └── InstitutionRequestDto.java
│   └── response/
│       ├── EventResponse.java
│       └── InstitutionResponse.java
├── model/
│   ├── EventModel.java
│   ├── InstitutionModel.java
│   └── EventStatus.java
├── repository/
│   ├── EventRepository.java
│   └── InstitutionRepository.java
├── service/
│   ├── EventService.java              # Lógica de eventos
│   ├── InstitutionService.java        # Lógica de instituições
│   └── EventSchedulerService.java     # ⏰ Agendamento 5/5min
├── exception/
│   ├── ResourceNotFoundException.java
│   └── GlobalExceptionHandler.java
└── EventLifecycleManagerApplication.java
🚦 COMO EXECUTAR
Pré-requisitos
Java 17+

Maven

Postman (opcional)

Passos rápidos
bash
# 1. Clone o repositório
git clone https://github.com/seu-usuario/event-lifecycle-manager.git

# 2. Entre na pasta
cd event-lifecycle-manager

# 3. Execute a aplicação
mvn spring-boot:run
A aplicação iniciará em: http://localhost:8080

📌 ENDEREÇOS IMPORTANTES
Recurso	URL
Swagger UI	http://localhost:8080/swagger-ui.html
H2 Console	http://localhost:8080/h2-console
API Docs (JSON)	http://localhost:8080/v3/api-docs
Configuração H2 Console:

JDBC URL: jdbc:h2:mem:eventdb

User: sa

Password: (vazio)

🧪 DADOS INICIAIS (DATALOADER)
O sistema já carrega automaticamente 5 instituições e 23 eventos para testes:

🏛️ Instituições:
Nome	Tipo
Cooperativa Central Aurora	Cooperativa de Crédito
Instituto Horizonte	Educação
Fundação Serra Verde	Saúde
Associação Cultural Monte Azul	Cultura
Centro de Inovação Sul	Tecnologia
🎟️ Eventos:
✅ Eventos futuros - Aguardando ativação

✅ Eventos ativos hoje - startDate = data atual

✅ Eventos expirados - Serão desativados pelo scheduler

📋 EXEMPLOS DE REQUISIÇÕES
1. CRIAR INSTITUIÇÃO
bash
curl -X POST http://localhost:8080/api/institutions \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Instituto de Tecnologia",
    "type": "Educação"
  }'
2. CRIAR EVENTO FUTURO
bash
curl -X POST http://localhost:8080/api/v1/events \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Workshop de Arquitetura de Software",
    "startDate": "2026-04-15",
    "endDate": "2026-04-17",
    "institutionId": 1
  }'
3. CRIAR EVENTO PARA HOJE (ATIVA AUTOMATICAMENTE)
bash
curl -X POST http://localhost:8080/api/v1/events \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Palestra: Boas Práticas em APIs",
    "startDate": "2026-02-12",
    "endDate": "2026-02-12",
    "institutionId": 1
  }'
4. LISTAR TODOS OS EVENTOS
bash
curl -X GET http://localhost:8080/api/v1/events
5. LISTAR EVENTOS ATIVOS
bash
curl -X GET http://localhost:8080/api/v1/events?active=true
6. LISTAR EVENTOS POR INSTITUIÇÃO
bash
curl -X GET http://localhost:8080/api/v1/events?institutionId=1
⏰ COMPORTAMENTO DO SCHEDULER
Intervalo	Ação	Log
A cada 5 minutos	Verifica eventos expirados	⏰ [2026-02-12] Analisando eventos expirados...
Quando encontra	Desativa automaticamente	✅ 3 evento(s) desativado(s) em 2026-02-12
Quando não encontra	Apenas informa	📭 Nenhum evento expirado encontrado
Meia-noite	Verificação extra	📅 Executando verificação diária
Exemplo de log:
text
2026-02-12T18:05:00.001 INFO  ⏰ [2026-02-12] Analisando eventos expirados...
2026-02-12T18:05:00.123 INFO  📌 Encontrados 2 eventos expirados
2026-02-12T18:05:00.124 INFO     ➡️ Evento ID: 5 - 'Curso Antigo' desativado
2026-02-12T18:05:00.125 INFO     ➡️ Evento ID: 8 - 'Palestra 2025' desativado
2026-02-12T18:05:00.500 INFO  ✅ 2 evento(s) desativado(s) com sucesso
✅ VALIDAÇÕES IMPLEMENTADAS
Campo	Validação	Mensagem
name	@NotBlank + @Size(3-100)	Nome é obrigatório / deve ter entre 3 e 100 caracteres
startDate	@NotNull + @FutureOrPresent	Data de início é obrigatória / deve ser hoje ou futura
endDate	@NotNull + @Future	Data de fim é obrigatória / deve ser futura
institutionId	@NotNull	ID da instituição é obrigatório
Regra de negócio	endDate > startDate	Data de fim deve ser posterior à data de início
📊 MODELO DE DADOS
sql
INSTITUTION {
  id: LONG (PK)
  name: STRING (unique)
  type: STRING
  created_at: DATETIME
  updated_at: DATETIME
}

EVENT {
  id: LONG (PK)
  name: STRING
  start_date: DATE
  end_date: DATE
  active: BOOLEAN
  institution_id: LONG (FK)
  created_at: DATETIME
  updated_at: DATETIME
}
🧪 TESTES MANUAIS RECOMENDADOS
bash
# 1. Listar instituições carregadas
curl -X GET http://localhost:8080/api/institutions

# 2. Listar eventos carregados
curl -X GET http://localhost:8080/api/v1/events

# 3. Verificar eventos ativos hoje
curl -X GET http://localhost:8080/api/v1/events?active=true

# 4. Aguardar scheduler executar (5min) e verificar logs
# 5. Criar novo evento e testar validações
# 6. Consultar evento por ID
📈 PRÓXIMAS EVOLUÇÕES
Adicionar testes unitários com JUnit e Mockito

Implementar paginação na listagem de eventos

Adicionar busca por período (data início/fim)

Incluir métricas de eventos ativos/inativos

Versão com banco PostgreSQL

Autenticação e autorização com Spring Security

👨‍💻 AUTOR
Gabriel Queiroz
Analista de Sistemas | Dedenvolvedor backend
