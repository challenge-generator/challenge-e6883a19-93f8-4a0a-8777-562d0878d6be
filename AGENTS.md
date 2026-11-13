# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Optimización de recursos en agentes de IA**.

| | |
|---|---|
| Tema | Optimización de componentes IA |
| Nivel | advanced-l2 |
| Chapter | Backend |
| Especialidad | Java |
| Stack | Java / Spring Boot 3.5 |
| Patron arquitectonico | hexagonal/clean |
| Tiempo estimado | 8 horas |

## Receta del stack

Esqueleto obligatorio:

- `pom.xml en la raiz`
- `clase con @SpringBootApplication`
- `application.yml en src/main/resources`
- `capa de dominio con entidades y puertos`
- `capa de aplicacion con casos de uso`
- `capa de infraestructura con adaptadores y @RestController`

Trampas conocidas:

- TODA `<version>` del pom va con tres segmentos: la del parent (ej. `3.5.6`) y la de cada dependencia que la lleve (ej. Resilience4j `2.2.0`). `3.4` y `2.0` no existen como artefacto y el build muere resolviendo dependencias.
- Las dependencias que el parent POM gestiona van SIN `<version>`: `spring-boot-starter-web`, `-data-jpa`, `-validation`, `-test`, etc.
- Resilience4j publica un artefacto por linea de Spring Boot. Con Spring Boot 3 va `resilience4j-spring-boot3` con version de tres segmentos (ej. `2.2.0`, no `2.0`). `resilience4j-spring-boot2` es de Spring Boot 2 y rompe el arranque.
- Si usas anotaciones de validacion (`@NotNull`, `@Size`, `@Positive`) declara `spring-boot-starter-validation`: el starter web no las trae.
- El `spring-boot-maven-plugin` tiene que estar en `<build><plugins>` o no se empaqueta ejecutable.
- Spring Boot 3 usa `jakarta.*`, nunca `javax.*`.
- Cada archivo empieza con su `package` y con un `import` por cada clase del proyecto que viva en otro paquete. Usar `PaymentService` desde `infrastructure` sin `import com.x.application.PaymentService` no compila.

Dependencias:

- org.springframework.boot:spring-boot-starter-web 3.5.6
- org.springframework.boot:spring-boot-starter-data-jpa n/a
- org.springframework.boot:spring-boot-starter-actuator n/a
- io.micrometer:micrometer-core n/a
- io.github.resilience4j:resilience4j-spring-boot3 2.2.0
- org.postgresql:postgresql n/a
- org.springframework.boot:spring-boot-starter-test n/a
- org.junit.jupiter:junit-jupiter-api n/a
- org.mockito:mockito-core n/a

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `mvn clean compile` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `mvn clean compile` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Análisis de consumo de recursos**: Informe de análisis de consumo de recursos que incluya gráficas y tablas comparativas.
- **Fase 2 — Implementación de optimizaciones**: Código optimizado del agente de IA con comentarios explicando las modificaciones realizadas.
- **Fase 3 — Evaluación y ajustes finales**: Reporte de evaluación que incluya resultados de pruebas de carga y ajustes realizados.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Lo que falta y tenes que completar

### 1. Referencias colgando (33)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/main/java/com/pragmabank/agent/AgentApplication.java` — `com.pragmabank.agent.infrastructure.adapter.ResourceMonitor`
      El import com.pragmabank.agent.infrastructure.adapter.ResourceMonitor usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/main/java/com/pragmabank/agent/AgentApplication.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragmabank/agent/infrastructure/adapter/JpaTransactionRepository.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragmabank/agent/application/usecase/ProcessTransactionUseCase.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragmabank/agent/infrastructure/adapter/MicrometerResourceMonitor.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragmabank/agent/infrastructure/config/ResourceMonitorConfig.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragmabank/agent/infrastructure/exception/GlobalExceptionHandler.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragmabank/agent/AgentApplication.java` — `ResourceMonitor.recordCpuUsage`
      Se invoca `recordCpuUsage` sobre `ResourceMonitor`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragmabank/agent/AgentApplication.java` — `ResourceMonitor.recordMemoryUsage`
      Se invoca `recordMemoryUsage` sobre `ResourceMonitor`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragmabank/agent/AgentApplication.java` — `ResourceMonitor.recordLatency`
      Se invoca `recordLatency` sobre `ResourceMonitor`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragmabank/agent/infrastructure/adapter/JpaTransactionRepository.java` — `Transaction.transactionId`
      Se invoca `transactionId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragmabank/agent/infrastructure/adapter/JpaTransactionRepository.java` — `Transaction.status`
      Se invoca `status` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragmabank/agent/application/usecase/ProcessTransactionUseCase.java` — `Transaction.status`
      Se invoca `status` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragmabank/agent/application/usecase/ProcessTransactionUseCase.java` — `Transaction.transactionId`
      Se invoca `transactionId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `TransactionRequest.transactionId`
      Se invoca `transactionId` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `TransactionRequest.amount`
      Se invoca `amount` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `TransactionRequest.currency`
      Se invoca `currency` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `TransactionRequest.description`
      Se invoca `description` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `Transaction.id`
      Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `Transaction.status`
      Se invoca `status` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `Transaction.amount`
      Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `Transaction.currency`
      Se invoca `currency` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `Transaction.description`
      Se invoca `description` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `Transaction.metadata`
      Se invoca `metadata` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `ProcessTransactionUseCase.getTransactionById`
      Se invoca `getTransactionById` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `ProcessTransactionUseCase.getTransactionsByStatus`
      Se invoca `getTransactionsByStatus` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `ProcessTransactionUseCase.deleteTransaction`
      Se invoca `deleteTransaction` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragmabank/agent/application/usecase/ProcessTransactionUseCaseTest.java` — `Transaction.status`
      Se invoca `status` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragmabank/agent/application/usecase/ProcessTransactionUseCaseTest.java` — `Transaction.amount`
      Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragmabank/agent/application/usecase/ProcessTransactionUseCaseTest.java` — `Transaction.currency`
      Se invoca `currency` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragmabank/agent/application/usecase/ProcessTransactionUseCaseTest.java` — `Transaction.description`
      Se invoca `description` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragmabank/agent/application/usecase/ProcessTransactionUseCaseTest.java` — `ResourceOptimizationException.getMessage`
      Se invoca `getMessage` sobre `ResourceOptimizationException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (15)

- `pom.xml`
- `src/main/java/com/pragmabank/agent/AgentApplication.java`
- `src/main/resources/application.yml`
- `src/main/java/com/pragmabank/agent/domain/model/Transaction.java`
- `src/main/java/com/pragmabank/agent/domain/port/TransactionRepository.java`
- `src/main/java/com/pragmabank/agent/domain/port/ResourceMonitor.java`
- `src/main/java/com/pragmabank/agent/infrastructure/adapter/JpaTransactionRepository.java`
- `src/main/java/com/pragmabank/agent/application/usecase/ProcessTransactionUseCase.java`
- `src/main/java/com/pragmabank/agent/infrastructure/adapter/MicrometerResourceMonitor.java`
- `src/main/java/com/pragmabank/agent/infrastructure/config/ResourceMonitorConfig.java`
- `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java`
- `src/main/java/com/pragmabank/agent/infrastructure/exception/ResourceOptimizationException.java`
- `src/main/java/com/pragmabank/agent/infrastructure/exception/GlobalExceptionHandler.java`
- `src/test/java/com/pragmabank/agent/application/usecase/ProcessTransactionUseCaseTest.java`
- `src/test/java/com/pragmabank/agent/infrastructure/adapter/MicrometerResourceMonitorTest.java`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/pragmabank/agent`
- `src/main/java/com/pragmabank/agent/domain`
- `src/main/java/com/pragmabank/agent/domain/model`
- `src/main/java/com/pragmabank/agent/domain/port`
- `src/main/java/com/pragmabank/agent/application`
- `src/main/java/com/pragmabank/agent/application/usecase`
- `src/main/java/com/pragmabank/agent/infrastructure`
- `src/main/java/com/pragmabank/agent/infrastructure/adapter`
- `src/main/java/com/pragmabank/agent/infrastructure/config`
- `src/main/java/com/pragmabank/agent/infrastructure/controller`
- `src/main/resources`
- `src/test/java/com/pragmabank/agent`

## Verificacion

```bash
mvn clean compile
```

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **hexagonal/clean**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Perfil: Chapter Backend, Especialidad Desarrollador, Tecnología Java, Advanced L2
- Brecha que el reto ataca: APLICA prácticas y estrategias que optimizan el uso de recursos con agentes IA., APLICA prácticas y estrategias que optimizan el uso de recursos con agentes IA.

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
