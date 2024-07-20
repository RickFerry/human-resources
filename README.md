![Screenshot 2024-07-20 120354](https://github.com/user-attachments/assets/603377b9-75d8-41df-89c5-1f9a7855a35d)
# Microsserviços Java com Spring Boot e Spring Cloud
## Atenção: curso específico para versões Java 11 e Spring Boot 2.3.4
#### Ricardo Ferreira Martins
- https://www.linkedin.com/in/ricardo-ferreira-martins-9a688214b/
- https://github.com/RickFerry

# Checklist baixar e executar projeto pronto

- JDK 11, variáveis PATH e JAVA_HOME
- Configurar IDE para pegar Java 11
- Importar projetos na IDE
- Configurar credenciais do config server
- Preparar Postman (collection e environment)
- Subir projetos em ordem:
    - Config server
    - Eureka server
    - Outros

# Comunicação simples, Feign, Ribbon

### 1.1 Criar projeto worker

### 1.2 Implementar projeto worker

Script SQL
```sql
INSERT INTO tb_workers (name, daily_Income) VALUES ('Bob', 200.0);
INSERT INTO tb_workers (name, daily_Income) VALUES ('Maria', 300.0);
INSERT INTO tb_workers (name, daily_Income) VALUES ('Alex', 250.0);
```

application.properties
```
spring.application.name=worker
server.port=8001

# Database configuration
spring.datasource.url=jdbc:h2:mem:db_worker
spring.datasource.username=sa
spring.datasource.password=

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### 1.3 Criar projeto payroll

application.properties
```
spring.application.name=payroll
server.port=8101
```

### 1.4 Implementar projeto payroll (mock)

### 1.5 RestTemplate

### 1.6 Feign

### 1.7 Ribbon load balancing

Run configuration
```
-Dserver.port=8002
```
# Eureka, Hystrix, Zuul

### 2.1 Criar projeto eureka-server

### 2.2 Configurar eureka-server

Porta padrão: 8761

Acessar o dashboard no navegador: http://localhost:8761

### 2.3 Configurar clientes Eureka

Eliminar o Ribbon de payroll:
- Dependência Maven
- Annotation no programa principal
- Configuração em application.properties

Atenção: aguardar um pouco depois de subir os microsserviços

### 2.4 Random port para worker

```
server.port=${PORT:0}

eureka.instance.instance-id=${spring.application.name}:${spring.application.instance_id:${random.value}}
```

Atenção: deletar as configurações múltiplas de execução de hr-worker

### 2.5 Tolerância a falhas com Hystrix

### 2.6 Timeout de Hystrix e Ribbon

Atenção: testar antes sem a annotation do Hystrix

```
hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=60000
ribbon.ConnectTimeout=10000
ribbon.ReadTimeout=20000
```

### 2.7 Criar projeto zuul-server

### 2.8 Configurar zuul-server

Porta padrão: 8765

### 2.9 Random port para payroll


### 2.10 Zuul timeout

Mesmo o timeout de Hystrix e Ribbon configurado em um microsserviço, se o Zuul não tiver seu timeout configurado, para ele será um problema de timeout. Então precisamos configurar o timeout no Zuul.

Se o timeout estiver configurado somente em Zuul, o Hystrix vai chamar o método alternativo no microsserviço específico.

# Configuração centralizada

### 3.1 Criar projeto config-server

### 3.2 Configurar projeto config-server

Quando um microsserviço é levantado, antes de se registrar no Eureka, ele busca as configurações no repositório central de configurações.

hr-worker.properties
```
test.config=My config value default profile
```
hr-worker-test.properties
```
test.config=My config value test profile
```
Teste:
```
http://localhost:8888/worker/default
http://localhost:8888/worker/test
```

### 3.3 worker como cliente do servidor de configuração, profiles ativos

No arquivo bootstrap.properties configuramos somente o que for relacionado com o servidor de configuração, e também o profile do projeto.

Atenção: as configurações do bootstrap.properties tem prioridade sobre as do application.properties

### 3.4 Actuator para atualizar configurações em runtime

Atenção: colocar @RefreshScope em toda classe que possua algum acesso às configurações

### 3.5 Repositório Git privativo

Atenção: reinicie a IDE depois de adicionar as variáveis de ambiente

# Autenticação e Autorização

### 4.1 Criar projeto user

### 4.2 Configurar projeto user

### 4.3 Entidades User, Role e associação N-N

### 4.4 Carga inicial do banco de dados
```sql
INSERT INTO tb_users (name, email, password) VALUES ('Nina Brown', 'nina@gmail.com', '$2a$10$NYFZ/8WaQ3Qb6FCs.00jce4nxX9w7AkgWVsQCG6oUwTAcZqP9Flqu');
INSERT INTO tb_users (name, email, password) VALUES ('Leia Red', 'leia@gmail.com', '$2a$10$NYFZ/8WaQ3Qb6FCs.00jce4nxX9w7AkgWVsQCG6oUwTAcZqP9Flqu');

INSERT INTO tb_roles (role_name) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_roles (role_name) VALUES ('ROLE_ADMIN');

INSERT INTO tb_users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_users_roles (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_users_roles (user_id, role_id) VALUES (2, 2);
```

### 4.5 UserRepository, UserResource, Zuul config

### 4.6 Criar projeto oauth

### 4.7 Configurar projeto oauth

### 4.8 UserFeignClient

### 4.9 Login e geração do Token JWT

Source -> Override -> configure(AuthenticationManagerBuilder)

Source -> Override -> authenticationManager()

Basic authorization = "Basic " + Base64.encode(client-id + ":" + client-secret)

### 4.10 Autorização de recursos pelo gateway Zuul

### 4.11 Deixando o Postman top

Variáveis:
- api-gateway: http://localhost:8765
- config-host: http://localhost:8888
- client-name: CLIENT-NAME
- client-secret: CLIENT-SECRET
- username: leia@gmail.com
- password: 123456
- token:

Script para atribuir token à variável de ambiente do Postman:
```js
if (responseCode.code >= 200 && responseCode.code < 300) {
    var json = JSON.parse(responseBody);
    postman.setEnvironmentVariable('token', json.access_token);
}
```
### 4.12 Configuração de segurança para o servidor de configuração

### 4.13 Configurando CORS

Teste no navegador:
```js
fetch("http://localhost:8765/hr-worker/workers", {
  "headers": {
    "accept": "*/*",
    "accept-language": "en-US,en;q=0.9,pt-BR;q=0.8,pt;q=0.7",
    "sec-fetch-dest": "empty",
    "sec-fetch-mode": "cors",
    "sec-fetch-site": "cross-site"
  },
  "referrer": "http://localhost:3000",
  "referrerPolicy": "no-referrer-when-downgrade",
  "body": null,
  "method": "GET",
  "mode": "cors",
  "credentials": "omit"
});
```
