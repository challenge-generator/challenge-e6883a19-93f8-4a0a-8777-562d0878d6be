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