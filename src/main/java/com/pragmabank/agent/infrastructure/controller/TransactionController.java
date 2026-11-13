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