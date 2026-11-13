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