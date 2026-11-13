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