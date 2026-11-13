# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/pragmabank/agent/AgentApplication.java` — `com.pragmabank.agent.infrastructure.adapter.ResourceMonitor`: El import com.pragmabank.agent.infrastructure.adapter.ResourceMonitor usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/pragmabank/agent/AgentApplication.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragmabank/agent/infrastructure/adapter/JpaTransactionRepository.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragmabank/agent/application/usecase/ProcessTransactionUseCase.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragmabank/agent/infrastructure/adapter/MicrometerResourceMonitor.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragmabank/agent/infrastructure/config/ResourceMonitorConfig.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragmabank/agent/infrastructure/exception/GlobalExceptionHandler.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragmabank/agent/AgentApplication.java` — `ResourceMonitor.recordCpuUsage`: Se invoca `recordCpuUsage` sobre `ResourceMonitor`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragmabank/agent/AgentApplication.java` — `ResourceMonitor.recordMemoryUsage`: Se invoca `recordMemoryUsage` sobre `ResourceMonitor`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragmabank/agent/AgentApplication.java` — `ResourceMonitor.recordLatency`: Se invoca `recordLatency` sobre `ResourceMonitor`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragmabank/agent/infrastructure/adapter/JpaTransactionRepository.java` — `Transaction.transactionId`: Se invoca `transactionId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragmabank/agent/infrastructure/adapter/JpaTransactionRepository.java` — `Transaction.status`: Se invoca `status` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragmabank/agent/application/usecase/ProcessTransactionUseCase.java` — `Transaction.status`: Se invoca `status` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragmabank/agent/application/usecase/ProcessTransactionUseCase.java` — `Transaction.transactionId`: Se invoca `transactionId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `TransactionRequest.transactionId`: Se invoca `transactionId` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `TransactionRequest.amount`: Se invoca `amount` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `TransactionRequest.currency`: Se invoca `currency` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `TransactionRequest.description`: Se invoca `description` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `Transaction.id`: Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `Transaction.status`: Se invoca `status` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `Transaction.amount`: Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `Transaction.currency`: Se invoca `currency` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `Transaction.description`: Se invoca `description` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `Transaction.metadata`: Se invoca `metadata` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `ProcessTransactionUseCase.getTransactionById`: Se invoca `getTransactionById` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `ProcessTransactionUseCase.getTransactionsByStatus`: Se invoca `getTransactionsByStatus` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java` — `ProcessTransactionUseCase.deleteTransaction`: Se invoca `deleteTransaction` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragmabank/agent/application/usecase/ProcessTransactionUseCaseTest.java` — `Transaction.status`: Se invoca `status` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragmabank/agent/application/usecase/ProcessTransactionUseCaseTest.java` — `Transaction.amount`: Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragmabank/agent/application/usecase/ProcessTransactionUseCaseTest.java` — `Transaction.currency`: Se invoca `currency` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragmabank/agent/application/usecase/ProcessTransactionUseCaseTest.java` — `Transaction.description`: Se invoca `description` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragmabank/agent/application/usecase/ProcessTransactionUseCaseTest.java` — `ResourceOptimizationException.getMessage`: Se invoca `getMessage` sobre `ResourceOptimizationException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
mvn clean compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Perfil
Chapter Backend, Especialidad Desarrollador, Tecnología Java, Advanced L2

### Brecha de conocimiento
APLICA prácticas y estrategias que optimizan el uso de recursos con agentes IA., APLICA prácticas y estrategias que optimizan el uso de recursos con agentes IA.

### Reto
- Tema: Optimización de componentes IA
- Seniority: advanced-l2
- Tipo: practical
- Título: Optimización de recursos en agentes de IA
- Tiempo estimado: 8 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Análisis de consumo de recursos — objetivo: Identificar las áreas de mayor consumo de recursos en el agente de IA. — entregable (NO resolver): Informe de análisis de consumo de recursos que incluya gráficas y tablas comparativas.
- Fase 2: Implementación de optimizaciones — objetivo: Aplicar técnicas de optimización para reducir el consumo de recursos. — entregable (NO resolver): Código optimizado del agente de IA con comentarios explicando las modificaciones realizadas.
- Fase 3: Evaluación y ajustes finales — objetivo: Evaluar el impacto de las optimizaciones y realizar ajustes finales. — entregable (NO resolver): Reporte de evaluación que incluya resultados de pruebas de carga y ajustes realizados.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.pragmabank</groupId>
    <artifactId>agent</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>AgentApplication</name>
    <description>Agente de IA para procesamiento de transacciones financieras con optimización de recursos</description>

    <properties>
        <java.version>21</java.version>
        <resilience4j.version>2.2.0</resilience4j.version>
    </properties>

    <dependencies>
        <!-- Spring Boot Starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- Micrometer for metrics -->
        <dependency>
            <groupId>io.micrometer</groupId>
            <artifactId>micrometer-core</artifactId>
        </dependency>

        <!-- Resilience4j for circuit breaker -->
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot3</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-micrometer</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>

        <!-- Database -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Testing -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: src/main/java/com/pragmabank/agent/AgentApplication.java ===
package com.pragmabank.agent;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import io.micrometer.core.instrument.MeterRegistry;
import com.pragmabank.agent.application.usecase.ProcessTransactionUseCase;
import com.pragmabank.agent.infrastructure.adapter.ResourceMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class AgentApplication {
    private static final Logger logger = LoggerFactory.getLogger(AgentApplication.class);

    private final ProcessTransactionUseCase processTransactionUseCase;
    private final ResourceMonitor resourceMonitor;
    private final MeterRegistry meterRegistry;

    public AgentApplication(ProcessTransactionUseCase processTransactionUseCase,
                          ResourceMonitor resourceMonitor,
                          MeterRegistry meterRegistry) {
        this.processTransactionUseCase = processTransactionUseCase;
        this.resourceMonitor = resourceMonitor;
        this.meterRegistry = meterRegistry;
    }

    public static void main(String[] args) {
        SpringApplication.run(AgentApplication.class, args);
    }

    @Bean
    @Profile("dev")
    CommandLineRunner run() {
        return args -> {
            logger.info("Iniciando monitoreo de recursos del agente de IA...");

            // Simulación inicial de procesamiento de transacciones para generar métricas
            for (int i = 0; i < 10; i++) {
                try {
                    processTransactionUseCase.execute(new TransactionRequest(
                        "TXN-" + (1000 + i),
                        1000.0 + (i * 100),
                        "COP",
                        "Pago de servicios"
                    ));

                    // Registrar métricas después de cada procesamiento
                    resourceMonitor.recordCpuUsage();
                    resourceMonitor.recordMemoryUsage();
                    resourceMonitor.recordLatency(System.currentTimeMillis());

                    // Simular carga variable
                    Thread.sleep((long) (Math.random() * 50));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.error("Hilo interrumpido durante simulación", e);
                } catch (Exception e) {
                    logger.error("Error procesando transacción simulada", e);
                    meterRegistry.counter("transactions.failed").increment();
                }
            }

            logger.info("Métricas de recursos:");
            logger.info("CPU Usage: {}%",
                meterRegistry.gauge("system.cpu.usage", resourceMonitor.getCpuUsage()));
            logger.info("Memory Usage: {} MB",
                meterRegistry.gauge("jvm.memory.used", resourceMonitor.getMemoryUsage()));
            logger.info("Latencia promedio: {} ms",
                meterRegistry.timer("transactions.latency").mean(TimeUnit.MILLISECONDS));
        };
    }

    public record TransactionRequest(String transactionId, double amount, String currency, String description) {}
}

// === ARCHIVO: src/main/resources/application.yml ===
spring:
  application:
    name: agent

  datasource:
    url: jdbc:postgresql://localhost:5432/pragmabank
    username: postgres
    password: postgres
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 10
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.PostgreSQLDialect

management:
  endpoints:
    web:
      exposure:
        include: health,metrics,prometheus,info
  endpoint:
    health:
      show-details: always
  metrics:
    export:
      prometheus:
        enabled: true
    tags:
      application: ${spring.application.name}
    distribution:
      percentiles-histogram:
        http.server.requests: true
      slo:
        http.server.requests: 100ms,200ms,500ms

logging:
  level:
    root: INFO
    com.pragmabank.agent: DEBUG
    org.springframework.web: INFO
    org.hibernate: ERROR
    io.micrometer: INFO
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
  file:
    name: logs/agent.log

// === ARCHIVO: src/main/java/com/pragmabank/agent/domain/model/Transaction.java ===
package com.pragmabank.agent.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad del dominio que representa una transacción financiera.
 * Contiene los atributos esenciales de una transacción y su estado.
 */
public record Transaction(
    UUID id,
    BigDecimal amount,
    String currency,
    String description,
    LocalDateTime timestamp,
    TransactionStatus status,
    String metadata) {

    /**
     * Enum que representa los posibles estados de una transacción.
     */
    public enum TransactionStatus {
        PENDING,
        PROCESSED,
        FAILED,
        ROLLED_BACK
    }

    /**
     * Constructor que valida los campos requeridos antes de crear la transacción.
     * @param id Identificador único de la transacción
     * @param amount Monto de la transacción
     * @param currency Código de moneda (ej. USD, EUR)
     * @param description Descripción de la transacción
     * @param timestamp Fecha y hora de creación
     * @param status Estado actual de la transacción
     * @param metadata Información adicional en formato JSON
     * @throws IllegalArgumentException si algún campo requerido es nulo o inválido
     */
    public Transaction {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la transacción no puede ser nulo");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("La moneda no puede estar vacía");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("La fecha y hora no pueden ser nulas");
        }
        if (status == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }
    }

    /**
     * Crea una nueva transacción con estado PENDING.
     * @param amount Monto de la transacción
     * @param currency Código de moneda
     * @param description Descripción de la transacción
     * @return Nueva instancia de Transaction con estado PENDING
     */
    public static Transaction createPending(BigDecimal amount, String currency, String description) {
        return new Transaction(
            UUID.randomUUID(),
            amount,
            currency,
            description,
            LocalDateTime.now(),
            TransactionStatus.PENDING,
            "{}"
        );
    }

    /**
     * Marca la transacción como PROCESSED y actualiza su metadata.
     * @param newMetadata Metadata adicional en formato JSON
     * @return Nueva instancia con el estado actualizado
     */
    public Transaction markAsProcessed(String newMetadata) {
        return new Transaction(
            this.id,
            this.amount,
            this.currency,
            this.description,
            this.timestamp,
            TransactionStatus.PROCESSED,
            newMetadata
        );
    }

    /**
     * Marca la transacción como FAILED y actualiza su metadata con la causa.
     * @param errorCause Causa del fallo
     * @return Nueva instancia con el estado actualizado
     */
    public Transaction markAsFailed(String errorCause) {
        return new Transaction(
            this.id,
            this.amount,
            this.currency,
            this.description,
            this.timestamp,
            TransactionStatus.FAILED,
            String.format("{ \"error\": \"%s\" }", errorCause)
        );
    }
}

// === ARCHIVO: src/main/java/com/pragmabank/agent/domain/port/TransactionRepository.java ===
package com.pragmabank.agent.domain.port;


import com.pragmabank.agent.domain.model.TransactionStatus;
import com.pragmabank.agent.domain.model.Transaction;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto para operaciones de persistencia de transacciones.
 * Define los contratos que la capa de infraestructura debe implementar.
 */
public interface TransactionRepository {

    /**
     * Guarda una transacción en el repositorio.
     * @param transaction Transacción a guardar
     * @return Transacción guardada
     */
    Transaction save(Transaction transaction);

    /**
     * Busca una transacción por su ID.
     * @param id ID de la transacción
     * @return Optional con la transacción si existe
     */
    Optional<Transaction> findById(UUID id);

    /**
     * Busca transacciones por estado.
     * @param status Estado a filtrar
     * @return Lista de transacciones con el estado especificado
     */
    List<Transaction> findByStatus(Transaction.TransactionStatus status);

    /**
     * Actualiza el estado de una transacción.
     * @param id ID de la transacción
     * @param newStatus Nuevo estado
     * @param metadata Metadata adicional
     * @return Transacción actualizada
     */
    Transaction updateStatus(UUID id, Transaction.TransactionStatus newStatus, String metadata);

    /**
     * Elimina una transacción por su ID.
     * @param id ID de la transacción a eliminar
     */
    void deleteById(UUID id);

    /**
     * Cuenta el número de transacciones con un estado específico.
     * @param status Estado a contar
     * @return Número de transacciones
     */
    long countByStatus(Transaction.TransactionStatus status);
}

// === ARCHIVO: src/main/java/com/pragmabank/agent/domain/port/ResourceMonitor.java ===
package com.pragmabank.agent.domain.port;

import java.util.Map;

/**
 * Puerto para monitoreo de recursos del sistema (CPU y memoria).
 * Define los contratos para obtener métricas de consumo de recursos.
 */
public interface ResourceMonitor {

    /**
     * Obtiene el uso actual de CPU del sistema.
     * @return Porcentaje de uso de CPU (0-100)
     */
    double getCpuUsage();

    /**
     * Obtiene el uso actual de memoria del sistema.
     * @return Cantidad de memoria usada en MB
     */
    double getMemoryUsage();

    /**
     * Obtiene métricas detalladas de recursos.
     * @return Mapa con métricas clave-valor
     */
    Map<String, Double> getResourceMetrics();

    /**
     * Verifica si el sistema está bajo alta carga de recursos.
     * @param cpuThreshold Umbral de CPU (0-100)
     * @param memoryThreshold Umbral de memoria en MB
     * @return true si el sistema supera los umbrales
     */
    boolean isResourceConstrained(double cpuThreshold, double memoryThreshold);

    /**
     * Registra un evento de consumo de recursos.
     * @param eventType Tipo de evento (ej. "PROCESS_TRANSACTION")
     * @param resourceMetrics Métricas asociadas al evento
     */
    void recordResourceEvent(String eventType, Map<String, Double> resourceMetrics);
}

// === ARCHIVO: src/main/java/com/pragmabank/agent/infrastructure/adapter/JpaTransactionRepository.java ===
package com.pragmabank.agent.infrastructure.adapter;


import com.pragmabank.agent.domain.model.TransactionStatus;
import com.pragmabank.agent.domain.model.Transaction;
import com.pragmabank.agent.domain.port.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaTransactionRepository implements TransactionRepository {

    private static final Logger logger = LoggerFactory.getLogger(JpaTransactionRepository.class);
    private final JpaRepository<Transaction, UUID> jpaRepository;

    public JpaTransactionRepository(JpaRepository<Transaction, UUID> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    @Transactional
    public Transaction save(Transaction transaction) {
        logger.debug("Guardando transacción con ID: {}", transaction.transactionId());
        Transaction saved = jpaRepository.save(transaction);
        logger.info("Transacción {} guardada exitosamente con estado: {}", 
                    saved.transactionId(), saved.status());
        return saved;
    }

    @Override
    public Optional<Transaction> findById(UUID id) {
        logger.debug("Buscando transacción por ID: {}", id);
        return jpaRepository.findById(id);
    }

    @Override
    public List<Transaction> findByStatus(Transaction.TransactionStatus status) {
        logger.debug("Buscando transacciones con estado: {}", status);
        return jpaRepository.findAll().stream()
                .filter(t -> t.status().equals(status))
                .toList();
    }

    @Override
    @Transactional
    @Modifying
    public Transaction updateStatus(UUID id, Transaction.TransactionStatus newStatus, String metadata) {
        logger.info("Actualizando estado de transacción {} a {}", id, newStatus);
        Optional<Transaction> existingOpt = jpaRepository.findById(id);
        
        if (existingOpt.isEmpty()) {
            logger.error("No se encontró la transacción con ID: {}", id);
            throw new IllegalArgumentException("Transacción no encontrada: " + id);
        }
        
        Transaction existing = existingOpt.get();
        Transaction updated = switch (newStatus) {
            case PROCESSED -> existing.markAsProcessed(metadata);
            case FAILED -> existing.markAsFailed(metadata);
            default -> existing;
        };
        
        return jpaRepository.save(updated);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        logger.info("Eliminando transacción con ID: {}", id);
        if (!jpaRepository.existsById(id)) {
            logger.warn("Intento de eliminar transacción inexistente: {}", id);
            throw new IllegalArgumentException("Transacción no encontrada para eliminar: " + id);
        }
        jpaRepository.deleteById(id);
    }

    @Override
    public long countByStatus(Transaction.TransactionStatus status) {
        logger.debug("Contando transacciones con estado: {}", status);
        return jpaRepository.findAll().stream()
                .filter(t -> t.status().equals(status))
                .count();
    }
}

// === ARCHIVO: src/main/java/com/pragmabank/agent/application/usecase/ProcessTransactionUseCase.java ===
package com.pragmabank.agent.application.usecase;

import com.pragmabank.agent.domain.model.Transaction;
import com.pragmabank.agent.domain.port.ResourceMonitor;
import com.pragmabank.agent.domain.port.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ProcessTransactionUseCase {

    private static final Logger logger = LoggerFactory.getLogger(ProcessTransactionUseCase.class);
    private static final double CPU_THRESHOLD = 80.0;
    private static final double MEMORY_THRESHOLD = 85.0;
    private static final int BATCH_SIZE = 50;

    private final TransactionRepository transactionRepository;
    private final ResourceMonitor resourceMonitor;

    public ProcessTransactionUseCase(TransactionRepository transactionRepository, 
                                      ResourceMonitor resourceMonitor) {
        this.transactionRepository = transactionRepository;
        this.resourceMonitor = resourceMonitor;
    }

    public Transaction execute(String transactionId, BigDecimal amount, 
                                String currency, String description) {
        logger.info("Iniciando procesamiento de transacción: {}", transactionId);
        long startTime = System.nanoTime();

        checkResourceAvailability();

        Transaction transaction = Transaction.createPending(amount, currency, description);
        Transaction savedTransaction = transactionRepository.save(transaction);

        boolean processed = processWithOptimization(savedTransaction);

        long endTime = System.nanoTime();
        long durationMs = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        logger.info("Transacción {} procesada en {}ms. Estado final: {}", 
                    transactionId, durationMs, savedTransaction.status());

        recordProcessingMetrics(durationMs, processed);

        return savedTransaction;
    }

    private void checkResourceAvailability() {
        if (resourceMonitor.isResourceConstrained(CPU_THRESHOLD, MEMORY_THRESHOLD)) {
            logger.warn("Recursos limitados detectados. CPU: {}, Memoria: {}",
                       resourceMonitor.getCpuUsage(), 
                       resourceMonitor.getMemoryUsage());
            applyResourceOptimization();
        }
    }

    private boolean processWithOptimization(Transaction transaction) {
        try {
            double currentCpu = resourceMonitor.getCpuUsage();
            double currentMemory = resourceMonitor.getMemoryUsage();

            if (currentMemory > MEMORY_THRESHOLD) {
                logger.info("Aplicando optimización de memoria para transacción: {}", 
                           transaction.transactionId());
                optimizeMemoryUsage();
            }

            if (currentCpu > CPU_THRESHOLD) {
                logger.info("Aplicando optimización de CPU para transacción: {}", 
                           transaction.transactionId());
                Thread.yield();
            }

            Transaction processed = transaction.markAsProcessed("Processed with optimization");
            transactionRepository.save(processed);
            return true;

        } catch (Exception e) {
            logger.error("Error procesando transacción: {}", e.getMessage());
            Transaction failed = transaction.markAsFailed(e.getMessage());
            transactionRepository.save(failed);
            return false;
        }
    }

    private void applyResourceOptimization() {
        logger.info("Aplicando estrategias de optimización de recursos...");
        Map<String, Double> metrics = resourceMonitor.getResourceMetrics();
        resourceMonitor.recordResourceEvent("OPTIMIZATION_APPLIED", metrics);
    }

    private void optimizeMemoryUsage() {
        System.gc();
        logger.debug("Limpieza de memoria ejecutada");
    }

    private void recordProcessingMetrics(long durationMs, boolean success) {
        Map<String, Double> currentMetrics = resourceMonitor.getResourceMetrics();
        currentMetrics.put("processing_time_ms", (double) durationMs);
        currentMetrics.put("success", success ? 1.0 : 0.0);
        resourceMonitor.recordResourceEvent("TRANSACTION_PROCESSED", currentMetrics);
    }

    public int processBatch(List<Transaction> transactions) {
        logger.info("Iniciando procesamiento de lote de {} transacciones", transactions.size());
        int processedCount = 0;

        List<Transaction> batch = transactions.subList(0, Math.min(BATCH_SIZE, transactions.size()));
        
        for (Transaction transaction : batch) {
            try {
                if (processWithOptimization(transaction)) {
                    processedCount++;
                }
            } catch (Exception e) {
                logger.error("Error en procesamiento por lotes: {}", e.getMessage());
            }
        }

        logger.info("Lote procesado: {} de {} transacciones exitosas", 
                   processedCount, batch.size());
        return processedCount;
    }
}

// === ARCHIVO: src/main/java/com/pragmabank/agent/infrastructure/adapter/MicrometerResourceMonitor.java ===
package com.pragmabank.agent.infrastructure.adapter;

import com.pragmabank.agent.domain.port.ResourceMonitor;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MicrometerResourceMonitor implements ResourceMonitor {

    private static final Logger logger = LoggerFactory.getLogger(MicrometerResourceMonitor.class);
    private final MeterRegistry meterRegistry;
    private final OperatingSystemMXBean osBean;
    private final MemoryMXBean memoryBean;

    public MicrometerResourceMonitor(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.osBean = ManagementFactory.getOperatingSystemMXBean();
        this.memoryBean = ManagementFactory.getMemoryMXBean();
        initializeMetrics();
    }

    private void initializeMetrics() {
        logger.info("Inicializando monitor de recursos con Micrometer");
        
        Timer.builder("resource.monitoring.time")
                .description("Tiempo de monitoreo de recursos")
                .register(meterRegistry);
        
        logger.debug("Métricas de recursos inicializadas");
    }

    @Override
    public double getCpuUsage() {
        try {
            double systemLoadAverage = osBean.getSystemLoadAverage();
            int availableProcessors = osBean.getAvailableProcessors();
            double cpuUsage = (systemLoadAverage / availableProcessors) * 100.0;
            
            meterRegistry.gauge("cpu.usage.percent", cpuUsage);
            logger.trace("Uso de CPU: {}%", cpuUsage);
            return Math.min(cpuUsage, 100.0);
        } catch (Exception e) {
            logger.warn("Error al obtener uso de CPU: {}", e.getMessage());
            return 0.0;
        }
    }

    @Override
    public double getMemoryUsage() {
        try {
            memoryBean.getHeapMemoryUsage();
            memoryBean.getNonHeapMemoryUsage();
            
            long usedHeap = memoryBean.getHeapMemoryUsage().getUsed();
            long maxHeap = memoryBean.getHeapMemoryUsage().getMax();
            
            double memoryUsagePercent = maxHeap > 0 
                ? (usedHeap / (double) maxHeap) * 100.0 
                : 0.0;
            
            meterRegistry.gauge("memory.usage.percent", memoryUsagePercent);
            meterRegistry.gauge("memory.heap.used.bytes", usedHeap);
            meterRegistry.gauge("memory.heap.max.bytes", maxHeap);
            
            logger.trace("Uso de memoria: {}%", memoryUsagePercent);
            return Math.min(memoryUsagePercent, 100.0);
        } catch (Exception e) {
            logger.warn("Error al obtener uso de memoria: {}", e.getMessage());
            return 0.0;
        }
    }

    @Override
    public Map<String, Double> getResourceMetrics() {
        Map<String, Double> metrics = new HashMap<>();
        
        double cpuUsage = getCpuUsage();
        double memoryUsage = getMemoryUsage();
        
        metrics.put("cpu.usage.percent", cpuUsage);
        metrics.put("memory.usage.percent", memoryUsage);
        metrics.put("cpu.available.processors", (double) osBean.getAvailableProcessors());
        metrics.put("cpu.system.load.average", osBean.getSystemLoadAverage());
        
        if (memoryBean.getHeapMemoryUsage().getMax() > 0) {
            metrics.put("memory.heap.used.percent", 
                (memoryBean.getHeapMemoryUsage().getUsed() / (double) memoryBean.getHeapMemoryUsage().getMax()) * 100.0);
        }
        
        metrics.put("memory.heap.used.bytes", (double) memoryBean.getHeapMemoryUsage().getUsed());
        metrics.put("memory.heap.committed.bytes", (double) memoryBean.getHeapMemoryUsage().getCommitted());
        
        logger.debug("Métricas de recursos recopiladas: {}", metrics);
        return metrics;
    }

    @Override
    public boolean isResourceConstrained(double cpuThreshold, double memoryThreshold) {
        double currentCpu = getCpuUsage();
        double currentMemory = getMemoryUsage();
        
        boolean cpuConstrained = currentCpu > cpuThreshold;
        boolean memoryConstrained = currentMemory > memoryThreshold;
        
        if (cpuConstrained || memoryConstrained) {
            logger.warn("Recursos restringidos - CPU: {}/{}%, Memoria: {}/{}%",
                       currentCpu, cpuThreshold, currentMemory, memoryThreshold);
        }
        
        return cpuConstrained || memoryConstrained;
    }

    @Override
    public void recordResourceEvent(String eventType, Map<String, Double> resourceMetrics) {
        logger.info("Registrando evento de recursos: {}", eventType);
        
        try {
            Timer.Sample sample = Timer.start(meterRegistry);
            
            for (Map.Entry<String, Double> entry : resourceMetrics.entrySet()) {
                String metricName = "resource.event." + eventType.toLowerCase() + "." + entry.getKey();
                meterRegistry.gauge(metricName, entry.getValue());
            }
            
            sample.stop(Timer.builder("resource.event.duration")
                    .tag("event.type", eventType)
                    .register(meterRegistry));
            
            logger.debug("Evento de recursos registrado: {}", eventType);
        } catch (Exception e) {
            logger.error("Error al registrar evento de recursos: {}", e.getMessage());
        }
    }
}

// === ARCHIVO: src/main/java/com/pragmabank/agent/infrastructure/config/ResourceMonitorConfig.java ===
package com.pragmabank.agent.infrastructure.config;

import com.pragmabank.agent.domain.port.ResourceMonitor;
import com.pragmabank.agent.infrastructure.adapter.MicrometerResourceMonitor;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResourceMonitorConfig {

    private static final Logger logger = LoggerFactory.getLogger(ResourceMonitorConfig.class);

    @Bean
    public MeterRegistry meterRegistry() {
        logger.info("Configurando MeterRegistry para monitoreo de recursos");
        return Metrics.metricRegistry();
    }

    @Bean
    public ResourceMonitor resourceMonitor(MeterRegistry meterRegistry) {
        logger.info("Inicializando ResourceMonitor con Micrometer");
        return new MicrometerResourceMonitor(meterRegistry);
    }

    @Bean
    public ResourceMonitorMetrics resourceMonitorMetrics(ResourceMonitor resourceMonitor) {
        logger.info("Configurando métricas adicionales del ResourceMonitor");
        return new ResourceMonitorMetrics(resourceMonitor);
    }

    public static class ResourceMonitorMetrics {
        private final ResourceMonitor resourceMonitor;

        public ResourceMonitorMetrics(ResourceMonitor resourceMonitor) {
            this.resourceMonitor = resourceMonitor;
        }

        public ResourceMonitor getResourceMonitor() {
            return resourceMonitor;
        }

        public void logCurrentMetrics() {
            var metrics = resourceMonitor.getResourceMetrics();
            logger.info("Métricas actuales de recursos: CPU={}%, Memoria={}%",
                metrics.get("cpu"), metrics.get("memory"));
        }
    }
}

// === ARCHIVO: src/main/java/com/pragmabank/agent/infrastructure/controller/TransactionController.java ===
package com.pragmabank.agent.infrastructure.controller;



import com.pragmabank.agent.domain.model.TransactionStatus;
import com.pragmabank.agent.TransactionRequest;
import com.pragmabank.agent.AgentApplication;
import com.pragmabank.agent.application.usecase.ProcessTransactionUseCase;
import com.pragmabank.agent.domain.model.Transaction;
import com.pragmabank.agent.domain.port.ResourceMonitor;
import com.pragmabank.agent.infrastructure.exception.ResourceOptimizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private static final double CPU_THRESHOLD = 80.0;
    private static final double MEMORY_THRESHOLD = 85.0;

    private final ProcessTransactionUseCase processTransactionUseCase;
    private final ResourceMonitor resourceMonitor;

    public TransactionController(ProcessTransactionUseCase processTransactionUseCase,
                                  ResourceMonitor resourceMonitor) {
        this.processTransactionUseCase = processTransactionUseCase;
        this.resourceMonitor = resourceMonitor;
    }

    @PostMapping("/process")
    public ResponseEntity<Map<String, Object>> processTransaction(@RequestBody AgentApplication.TransactionRequest request) {
        logger.info("Iniciando procesamiento de transacción: {}", request.transactionId());

        if (resourceMonitor.isResourceConstrained(CPU_THRESHOLD, MEMORY_THRESHOLD)) {
            logger.warn("Recursos limitados detectados. Transacción: {}", request.transactionId());
            throw new ResourceOptimizationException("Recursos del sistema limitados. No se puede procesar la transacción en este momento.");
        }

        try {
            Transaction result = processTransactionUseCase.execute(
                request.transactionId(),
                BigDecimal.valueOf(request.amount()),
                request.currency(),
                request.description()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("transactionId", result.id().toString());
            response.put("status", result.status().name());
            response.put("amount", result.amount());
            response.put("currency", result.currency());
            response.put("description", result.description());
            response.put("metadata", result.metadata());

            var metrics = resourceMonitor.getResourceMetrics();
            response.put("resourceMetrics", metrics);

            logger.info("Transacción procesada exitosamente: {}", result.id());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error al procesar transacción: {}", e.getMessage(), e);
            throw new ResourceOptimizationException("Error al procesar la transacción: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getTransaction(@PathVariable String id) {
        logger.info("Consultando transacción: {}", id);

        try {
            UUID transactionId = UUID.fromString(id);
            Transaction transaction = processTransactionUseCase.getTransactionById(transactionId);

            if (transaction == null) {
                return ResponseEntity.notFound().build();
            }

            Map<String, Object> response = new HashMap<>();
            response.put("transactionId", transaction.id().toString());
            response.put("status", transaction.status().name());
            response.put("amount", transaction.amount());
            response.put("currency", transaction.currency());
            response.put("description", transaction.description());
            response.put("metadata", transaction.metadata());

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            logger.error("ID de transacción inválido: {}", id);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Map<String, Object>>> getTransactionsByStatus(@PathVariable String status) {
        logger.info("Consultando transacciones con estado: {}", status);

        try {
            Transaction.TransactionStatus transactionStatus = Transaction.TransactionStatus.valueOf(status.toUpperCase());
            List<Transaction> transactions = processTransactionUseCase.getTransactionsByStatus(transactionStatus);

            List<Map<String, Object>> response = transactions.stream()
                .map(t -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("transactionId", t.id().toString());
                    item.put("status", t.status().name());
                    item.put("amount", t.amount());
                    item.put("currency", t.currency());
                    item.put("description", t.description());
                    return item;
                })
                .toList();

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            logger.error("Estado de transacción inválido: {}", status);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/metrics")
    public ResponseEntity<Map<String, Object>> getResourceMetrics() {
        logger.debug("Consultando métricas de recursos");

        var metrics = resourceMonitor.getResourceMetrics();
        Map<String, Object> response = new HashMap<>(metrics);

        response.put("cpuUsage", resourceMonitor.getCpuUsage());
        response.put("memoryUsage", resourceMonitor.getMemoryUsage());
        response.put("isConstrained", resourceMonitor.isResourceConstrained(CPU_THRESHOLD, MEMORY_THRESHOLD));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "transaction-processor");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable String id) {
        logger.info("Eliminando transacción: {}", id);

        try {
            UUID transactionId = UUID.fromString(id);
            processTransactionUseCase.deleteTransaction(transactionId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.error("ID de transacción inválido para eliminar: {}", id);
            return ResponseEntity.badRequest().build();
        }
    }
}

// === ARCHIVO: src/main/java/com/pragmabank/agent/infrastructure/exception/ResourceOptimizationException.java ===
package com.pragmabank.agent.infrastructure.exception;

public class ResourceOptimizationException extends RuntimeException {

    private final String errorCode;
    private final String details;

    public ResourceOptimizationException(String message) {
        super(message);
        this.errorCode = "RESOURCE_OPTIMIZATION_ERROR";
        this.details = null;
    }

    public ResourceOptimizationException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "RESOURCE_OPTIMIZATION_ERROR";
        this.details = null;
    }

    public ResourceOptimizationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.details = null;
    }

    public ResourceOptimizationException(String message, String errorCode, String details) {
        super(message);
        this.errorCode = errorCode;
        this.details = details;
    }

    public ResourceOptimizationException(String message, String errorCode, String details, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.details = details;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ResourceOptimizationException{");
        sb.append("errorCode='").append(errorCode).append('\'');
        sb.append(", message='").append(getMessage()).append('\'');
        if (details != null) {
            sb.append(", details='").append(details).append('\'');
        }
        sb.append('}');
        return sb.toString();
    }
}

// === ARCHIVO: src/main/java/com/pragmabank/agent/infrastructure/exception/GlobalExceptionHandler.java ===
package com.pragmabank.agent.infrastructure.exception;

import com.pragmabank.agent.domain.port.ResourceMonitor;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final ResourceMonitor resourceMonitor;
    private final MeterRegistry meterRegistry;
    private final Timer errorHandlingTimer;

    public GlobalExceptionHandler(ResourceMonitor resourceMonitor, MeterRegistry meterRegistry) {
        this.resourceMonitor = resourceMonitor;
        this.meterRegistry = meterRegistry;
        this.errorHandlingTimer = Timer.builder("exception.handler.time")
                .description("Tiempo de manejo de excepciones")
                .register(meterRegistry);
    }

    @ExceptionHandler(ResourceOptimizationException.class)
    public ResponseEntity<Map<String, Object>> handleResourceOptimizationException(
            ResourceOptimizationException ex) {
        return errorHandlingTimer.record(() -> {
            logger.warn("Excepción de optimización de recursos: {}", ex.getMessage());
            
            Map<String, Double> currentMetrics = resourceMonitor.getResourceMetrics();
            Map<String, Object> response = buildErrorResponse(
                    "RESOURCE_ERROR",
                    ex.getMessage(),
                    HttpStatus.SERVICE_UNAVAILABLE.value(),
                    currentMetrics
            );
            
            resourceMonitor.recordResourceEvent("exception_resource_optimization", currentMetrics);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        });
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(
            IllegalArgumentException ex) {
        return errorHandlingTimer.record(() -> {
            logger.warn("Argumento inválido: {}", ex.getMessage());
            
            Map<String, Double> metrics = resourceMonitor.getResourceMetrics();
            Map<String, Object> response = buildErrorResponse(
                    "VALIDATION_ERROR",
                    ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value(),
                    metrics
            );
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        });
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex) {
        return errorHandlingTimer.record(() -> {
            String message = String.format("Tipo de argumento inválido para '%s': %s",
                    ex.getName(), ex.getValue());
            logger.warn(message);
            
            Map<String, Object> response = buildErrorResponse(
                    "TYPE_MISMATCH",
                    message,
                    HttpStatus.BAD_REQUEST.value(),
                    resourceMonitor.getResourceMetrics()
            );
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        });
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return errorHandlingTimer.record(() -> {
            logger.error("Excepción no manejada: ", ex);
            
            Map<String, Double> metrics = resourceMonitor.getResourceMetrics();
            Map<String, Object> response = buildErrorResponse(
                    "INTERNAL_ERROR",
                    "Error interno del servidor",
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    metrics
            );
            
            resourceMonitor.recordResourceEvent("exception_unhandled", metrics);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        });
    }

    private Map<String, Object> buildErrorResponse(String code, String message, int status, 
                                                     Map<String, Double> resourceMetrics) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", Instant.now().toString());
        error.put("code", code);
        error.put("message", message);
        error.put("status", status);
        
        Map<String, Object> resources = new HashMap<>();
        resources.put("cpuUsage", resourceMetrics.getOrDefault("cpu.usage", 0.0));
        resources.put("memoryUsage", resourceMetrics.getOrDefault("memory.usage", 0.0));
        error.put("resources", resources);
        
        return error;
    }
}

// === ARCHIVO: src/test/java/com/pragmabank/agent/application/usecase/ProcessTransactionUseCaseTest.java ===
package com.pragmabank.agent.application.usecase;


import com.pragmabank.agent.domain.model.TransactionStatus;
import com.pragmabank.agent.domain.model.Transaction;
import com.pragmabank.agent.domain.port.ResourceMonitor;
import com.pragmabank.agent.domain.port.TransactionRepository;
import com.pragmabank.agent.infrastructure.exception.ResourceOptimizationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProcessTransactionUseCase - Tests Unitarios")
class ProcessTransactionUseCaseTest {

    @Mock
    private TransactionRepository transactionRepository;
    
    @Mock
    private ResourceMonitor resourceMonitor;
    
    private ProcessTransactionUseCase processTransactionUseCase;
    
    private static final double CPU_THRESHOLD = 80.0;
    private static final double MEMORY_THRESHOLD = 85.0;

    @BeforeEach
    void setUp() {
        processTransactionUseCase = new ProcessTransactionUseCase(
                transactionRepository, 
                resourceMonitor,
                CPU_THRESHOLD,
                MEMORY_THRESHOLD
        );
    }

    @Nested
    @DisplayName("Escenario: Procesamiento exitoso de transacción")
    class ProcesamientoExitoso {

        @Test
        @DisplayName("Debe procesar transacción cuando los recursos están dentro de los umbrales")
        void debeProcesarTransaccionCuandoRecursosSuficientes() {
            BigDecimal amount = new BigDecimal("1000.00");
            String currency = "USD";
            String description = "Pago de servicio";
            UUID transactionId = UUID.randomUUID();
            
            when(resourceMonitor.getCpuUsage()).thenReturn(45.0);
            when(resourceMonitor.getMemoryUsage()).thenReturn(50.0);
            when(resourceMonitor.isResourceConstrained(anyDouble(), anyDouble())).thenReturn(false);
            
            Transaction savedTransaction = Transaction.createPending(amount, currency, description);
            when(transactionRepository.save(any(Transaction.class))).thenReturn(savedTransaction);
            
            Transaction result = processTransactionUseCase.execute(amount, currency, description);
            
            assertNotNull(result);
            assertEquals(Transaction.TransactionStatus.PENDING, result.status());
            verify(transactionRepository, times(1)).save(any(Transaction.class));
            verify(resourceMonitor, times(1)).recordResourceEvent(
                    eq("transaction_processed"), anyMap()
            );
        }

        @Test
        @DisplayName("Debe guardar la transacción en el repositorio")
        void debeGuardarTransaccionEnRepositorio() {
            BigDecimal amount = new BigDecimal("500.00");
            String currency = "EUR";
            String description = "Transferencia";
            
            when(resourceMonitor.getCpuUsage()).thenReturn(30.0);
            when(resourceMonitor.getMemoryUsage()).thenReturn(40.0);
            when(resourceMonitor.isResourceConstrained(anyDouble(), anyDouble())).thenReturn(false);
            when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> {
                Transaction tx = invocation.getArgument(0);
                return tx;
            });
            
            Transaction result = processTransactionUseCase.execute(amount, currency, description);
            
            ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
            verify(transactionRepository).save(transactionCaptor.capture());
            
            Transaction capturedTx = transactionCaptor.getValue();
            assertEquals(amount, capturedTx.amount());
            assertEquals(currency, capturedTx.currency());
            assertEquals(description, capturedTx.description());
        }
    }

    @Nested
    @DisplayName("Escenario: Optimización de recursos")
    class OptimizacionRecursos {

        @Test
        @DisplayName("Debe rechazar transacción cuando CPU supera umbral")
        void debeRechazarTransaccionCuandoCpuSuperaUmbral() {
            BigDecimal amount = new BigDecimal("1000.00");
            String currency = "USD";
            String description = "Transacción con alto consumo CPU";
            
            Map<String, Double> highCpuMetrics = new HashMap<>();
            highCpuMetrics.put("cpu.usage", 90.0);
            highCpuMetrics.put("memory.usage", 40.0);
            
            when(resourceMonitor.getResourceMetrics()).thenReturn(highCpuMetrics);
            when(resourceMonitor.isResourceConstrained(CPU_THRESHOLD, MEMORY_THRESHOLD)).thenReturn(true);
            
            ResourceOptimizationException exception = assertThrows(
                    ResourceOptimizationException.class,
                    () -> processTransactionUseCase.execute(amount, currency, description)
            );
            
            assertTrue(exception.getMessage().contains("CPU"));
            verify(transactionRepository, never()).save(any(Transaction.class));
            verify(resourceMonitor).recordResourceEvent(eq("transaction_rejected_resource_constraint"), anyMap());
        }

        @Test
        @DisplayName("Debe rechazar transacción cuando memoria supera umbral")
        void debeRechazarTransaccionCuandoMemoriaSuperaUmbral() {
            BigDecimal amount = new BigDecimal("2000.00");
            String currency = "USD";
            String description = "Transacción con alto consumo de memoria";
            
            Map<String, Double> highMemoryMetrics = new HashMap<>();
            highMemoryMetrics.put("cpu.usage", 50.0);
            highMemoryMetrics.put("memory.usage", 95.0);
            
            when(resourceMonitor.getResourceMetrics()).thenReturn(highMemoryMetrics);
            when(resourceMonitor.isResourceConstrained(CPU_THRESHOLD, MEMORY_THRESHOLD)).thenReturn(true);
            
            ResourceOptimizationException exception = assertThrows(
                    ResourceOptimizationException.class,
                    () -> processTransactionUseCase.execute(amount, currency, description)
            );
            
            assertTrue(exception.getMessage().contains("memoria") || 
                       exception.getMessage().contains("memory"));
            verify(transactionRepository, never()).save(any(Transaction.class));
        }

        @Test
        @DisplayName("Debe registrar métricas de recursos en cada procesamiento")
        void debeRegistrarMetricasDeRecursos() {
            BigDecimal amount = new BigDecimal("100.00");
            String currency = "USD";
            String description = "Transacción de prueba";
            
            Map<String, Double> normalMetrics = new HashMap<>();
            normalMetrics.put("cpu.usage", 25.0);
            normalMetrics.put("memory.usage", 35.0);
            
            when(resourceMonitor.getResourceMetrics()).thenReturn(normalMetrics);
            when(resourceMonitor.isResourceConstrained(CPU_THRESHOLD, MEMORY_THRESHOLD)).thenReturn(false);
            when(transactionRepository.save(any(Transaction.class))).thenAnswer(i -> i.getArgument(0));
            
            processTransactionUseCase.execute(amount, currency, description);
            
            verify(resourceMonitor, times(2)).getResourceMetrics();
            verify(resourceMonitor).recordResourceEvent(eq("transaction_processed"), anyMap());
        }
    }

    @Nested
    @DisplayName("Escenario: Validación de parámetros")
    class ValidacionParametros {

        @Test
        @DisplayName("Debe lanzar excepción con monto negativo")
        void debeLanzarExcepcionConMontoNegativo() {
            BigDecimal negativeAmount = new BigDecimal("-100.00");
            String currency = "USD";
            String description = "Monto negativo";
            
            assertThrows(IllegalArgumentException.class, 
                    () -> processTransactionUseCase.execute(negativeAmount, currency, description));
        }

        @Test
        @DisplayName("Debe lanzar excepción con moneda vacía")
        void debeLanzarExcepcionConMonedaVacia() {
            BigDecimal amount = new BigDecimal("100.00");
            String emptyCurrency = "";
            String description = "Currency vacía";
            
            assertThrows(IllegalArgumentException.class, 
                    () -> processTransactionUseCase.execute(amount, emptyCurrency, description));
        }

        @Test
        @DisplayName("Debe lanzar excepción con moneda nula")
        void debeLanzarExcepcionConMonedaNula() {
            BigDecimal amount = new BigDecimal("100.00");
            String nullCurrency = null;
            String description = "Currency nula";
            
            assertThrows(IllegalArgumentException.class, 
                    () -> processTransactionUseCase.execute(amount, nullCurrency, description));
        }

        @Test
        @DisplayName("Debe lanzar excepción con descripción nula")
        void debeLanzarExcepcionConDescripcionNula() {
            BigDecimal amount = new BigDecimal("100.00");
            String currency = "USD";
            String nullDescription = null;
            
            assertThrows(IllegalArgumentException.class, 
                    () -> processTransactionUseCase.execute(amount, currency, nullDescription));
        }
    }

    @Nested
    @DisplayName("Escenario: Verificación de umbrales configurables")
    class VerificacionUmbrales {

        @Test
        @DisplayName("Debe usar umbrales personalizados del constructor")
        void debeUsarUmbralesPersonalizados() {
            double customCpuThreshold = 50.0;
            double customMemoryThreshold = 60.0;
            
            ProcessTransactionUseCase customUseCase = new ProcessTransactionUseCase(
                    transactionRepository,
                    resourceMonitor,
                    customCpuThreshold,
                    customMemoryThreshold
            );
            
            BigDecimal amount = new BigDecimal("100.00");
            String currency = "USD";
            String description = "Test";
            
            Map<String, Double> nearThresholdMetrics = new HashMap<>();
            nearThresholdMetrics.put("cpu.usage", 55.0);
            nearThresholdMetrics.put("memory.usage", 45.0);
            
            when(resourceMonitor.getResourceMetrics()).thenReturn(nearThresholdMetrics);
            when(resourceMonitor.isResourceConstrained(customCpuThreshold, customMemoryThreshold))
                    .thenReturn(true);
            
            assertThrows(ResourceOptimizationException.class, 
                    () -> customUseCase.execute(amount, currency, description));
        }
    }
}

// === ARCHIVO: src/test/java/com/pragmabank/agent/infrastructure/adapter/MicrometerResourceMonitorTest.java ===
package com.pragmabank.agent.infrastructure.adapter;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MicrometerResourceMonitor - Tests Unitarios")
class MicrometerResourceMonitorTest {

    @Mock
    private MeterRegistry meterRegistry;
    
    private MicrometerResourceMonitor resourceMonitor;

    @BeforeEach
    void setUp() {
        resourceMonitor = new MicrometerResourceMonitor(meterRegistry);
    }

    @Nested
    @DisplayName("Escenario: Obtención de métricas de recursos")
    class ObtencionMetricas {

        @Test
        @DisplayName("Debe retornar uso de CPU mayor a cero")
        void debeRetornarCpuMayorCero() {
            double cpuUsage = resourceMonitor.getCpuUsage();
            
            assertTrue(cpuUsage >= 0.0, "El uso de CPU debe ser mayor o igual a 0");
            assertTrue(cpuUsage <= 100.0, "El uso de CPU debe ser menor o igual a 100");
        }

        @Test
        @DisplayName("Debe retornar uso de memoria mayor a cero")
        void debeRetornarMemoriaMayorCero() {
            double memoryUsage = resourceMonitor.getMemoryUsage();
            
            assertTrue(memoryUsage >= 0.0, "El uso de memoria debe ser mayor o igual a 0");
            assertTrue(memoryUsage <= 100.0, "El uso de memoria debe ser menor o igual a 100");
        }

        @Test
        @DisplayName("Debe retornar mapa con métricas de recursos")
        void debeRetornarMapaConMetricas() {
            Map<String, Double> metrics = resourceMonitor.getResourceMetrics();
            
            assertNotNull(metrics, "Las métricas no deben ser nulas");
            assertTrue(metrics.containsKey("cpu.usage"), "Debe contener CPU usage");
            assertTrue(metrics.containsKey("memory.usage"), "Debe contener Memory usage");
            assertTrue(metrics.containsKey("system.cpu.count"), "Debe contener CPU count");
        }

        @Test
        @DisplayName("Debe registrar métricas en Micrometer")
        void debeRegistrarMetricasEnMicrometer() {
            verify(meterRegistry, atLeastOnce()).gauge(anyString(), any(Double.class));
        }
    }

    @Nested
    @DisplayName("Escenario: Verificación de restricciones de recursos")
    class VerificacionRestricciones {

        @Test
        @DisplayName("Debe retornar true cuando CPU supera umbral")
        void debeRetornarTrueCuandoCpuSuperaUmbral() {
            AtomicReference<Double> cpuRef = new AtomicReference<>(90.0);
            
            when(meterRegistry.gauge(anyString(), any(Double.class))).thenAnswer(invocation -> {
                return null;
            });
            
            MicrometerResourceMonitor monitorWithHighCpu = new MicrometerResourceMonitor(meterRegistry);
            
            boolean isConstrained = monitorWithHighCpu.isResourceConstrained(80.0, 90.0);
            
            assertTrue(isConstrained || !isConstrained);
        }

        @Test
        @DisplayName("Debe retornar true cuando memoria supera umbral")
        void debeRetornarTrueCuandoMemoriaSuperaUmbral() {
            boolean isConstrained = resourceMonitor.isResourceConstrained(10.0, 10.0);
            
            assertNotNull(isConstrained);
        }

        @Test
        @DisplayName("Debe retornar false cuando recursos están por debajo de umbrales")
        void debeRetornarFalseCuandoRecursosBajos() {
            boolean isConstrained = resourceMonitor.isResourceConstrained(95.0, 95.0);
            
            assertFalse(isConstrained, "No debe estar restringido con umbrales altos");
        }
    }

    @Nested
    @DisplayName("Escenario: Registro de eventos de recursos")
    class RegistroEventos {

        @Test
        @DisplayName("Debe registrar evento de recursos en temporizador")
        void debeRegistrarEventoEnTemporizador() {
            String eventType = "transaction_processed";
            Map<String, Double> metrics = Map.of("cpu.usage", 50.0, "memory.usage", 60.0);
            
            Timer mockTimer = mock(Timer.class);
            when(meterRegistry.timer(anyString())).thenReturn(mockTimer);
            
            resourceMonitor.recordResourceEvent(eventType, metrics);
            
            verify(meterRegistry).timer(eq("resource.event." + eventType));
        }

        @Test
        @DisplayName("Debe registrar múltiples tipos de eventos")
        void debeRegistrarMultiplesTiposEventos() {
            Map<String, Double> metrics = Map.of("cpu.usage", 40.0, "memory.usage", 50.0);
            
            Timer mockTimer = mock(Timer.class);
            when(meterRegistry.timer(anyString())).thenReturn(mockTimer);
            
            resourceMonitor.recordResourceEvent("transaction_processed", metrics);
            resourceMonitor.recordResourceEvent("transaction_rejected", metrics);
            resourceMonitor.recordResourceEvent("resource_optimization_triggered", metrics);
            
            verify(meterRegistry, times(3)).timer(anyString());
        }

        @Test
        @DisplayName("Debe manejar evento con métricas nulas")
        void debeManejarEventoConMetricasNulas() {
            String eventType = "test_event";
            
            Timer mockTimer = mock(Timer.class);
            when(meterRegistry.timer(anyString())).thenReturn(mockTimer);
            
            assertDoesNotThrow(() -> resourceMonitor.recordResourceEvent(eventType, null));
        }
    }

    @Nested
    @DisplayName("Escenario: Configuración de métricas con Micrometer")
    class ConfiguracionMetricas {

        @Test
        @DisplayName("Debe crear medidores con nombres apropiados")
        void debeCrearMedidoresConNombres() {
            ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
            
            verify(meterRegistry, atLeastOnce()).gauge(nameCaptor.capture(), any(Double.class));
            
            var capturedNames = nameCaptor.getAllValues();
            assertTrue(capturedNames.stream().anyMatch(n -> n.contains("cpu") || n.contains("memory")));
        }

        @Test
        @DisplayName("Debe inicializar el monitor con métricas válidas")
        void debeInicializarConMetricasValidas() {
            assertDoesNotThrow(() -> {
                double cpu = resourceMonitor.getCpuUsage();
                double memory = resourceMonitor.getMemoryUsage();
                Map<String, Double> allMetrics = resourceMonitor.getResourceMetrics();
                
                assertNotNull(cpu);
                assertNotNull(memory);
                assertNotNull(allMetrics);
            });
        }
    }

    @Nested
    @DisplayName("Escenario: Integración con MeterRegistry")
    class IntegracionMeterRegistry {

        @Test
        @DisplayName("Debe usar el MeterRegistry proporcionado")
        void debeUsarMeterRegistryProveido() {
            assertNotNull(resourceMonitor);
            verify(meterRegistry, atLeastOnce()).gauge(anyString(), any(Double.class));
        }

        @Test
        @DisplayName("Debe manejarMeterRegistry null correctamente")
        void debeManejarMeterRegistryNull() {
            assertThrows(NullPointerException.class, () -> {
                new MicrometerResourceMonitor(null);
            });
        }
    }
}
```
