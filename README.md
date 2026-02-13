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


Regras de negócio implementadas:

✅ Ativação imediata	startDate = hoje	active = true

✅ Ativação programada	startDate ≤ hoje ≤ endDate	active = true (scheduler)

✅ Desativação automática	endDate < hoje	active = false (a cada 5min)

✅ Validação de período	endDate < startDate	❌ Erro 400

✅ Instituições após sua criação não podem ser deletadas, apenas eventos (Poderia futuramente adicionar a funcionalidade de um IN_DELETE no registro)

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

🚦 COMO EXECUTAR
Pré-requisitos

Java 17+

Maven

Postman (opcional)

Passos rápidos
bash
1. Clone o repositório
git clone https://github.com/seu-usuario/event-lifecycle-manager.git

2. Entre na pasta
cd event-lifecycle-manager

3. Execute a aplicação
mvn spring-boot:run
A aplicação iniciará em: http://localhost:8080

📌 ENDEREÇOS IMPORTANTES

Base URL http://localhost:8080

Swagger UI	http://localhost:8080/swagger-ui.html

H2 Console	http://localhost:8080/h2-console

Configuração H2 Console:

JDBC URL: jdbc:h2:mem:eventdb

User: sa

Password: (vazio)

🧪 DADOS INICIAIS (DATALOADER)

O sistema já carrega automaticamente 5 instituições e 23 eventos para testes:

🏛️ Instituições:
Cooperativa Central Aurora

Cooperativa de Crédito

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
curl -X POST http://localhost:8080/api/institutions/institution \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Instituto de Tecnologia",
    "type": "Educação"
  }'


2. CRIAR EVENTO FUTURO
bash
curl -X POST http://localhost:8080/api/v1/events/event \
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

A cada 2 minutos	
Verifica eventos expirados	

⏰ [2026-02-12] Analisando eventos expirados...
Quando encontra	Desativa automaticamente	

✅ 3 evento(s) desativado(s) em 2026-02-12
Quando não encontra	Apenas informa	

📭 Nenhum evento expirado encontrado
Meia-noite	Verificação extra	

📅 Executando verificação diária

Exemplo de log:
text
2026-02-12T18:05:00.001 INFO  ⏰ [2026-02-12] Analisando eventos expirados...
2026-02-12T18:05:00.123 INFO  📌 Encontrados 2 eventos expirados
2026-02-12T18:05:00.124 INFO     ➡️ Evento ID: 5 - 'Curso Antigo' desativado
2026-02-12T18:05:00.125 INFO     ➡️ Evento ID: 8 - 'Palestra 2025' desativado
2026-02-12T18:05:00.500 INFO  ✅ 2 evento(s) desativado(s) com sucesso

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


👨‍💻 AUTOR
Gabriel Queiroz
Analista de Sistemas | Dedenvolvedor backend
