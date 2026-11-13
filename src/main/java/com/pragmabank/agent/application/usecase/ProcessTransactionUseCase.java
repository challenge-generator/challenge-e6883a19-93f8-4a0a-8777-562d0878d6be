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