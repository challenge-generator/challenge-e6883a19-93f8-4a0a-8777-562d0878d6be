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