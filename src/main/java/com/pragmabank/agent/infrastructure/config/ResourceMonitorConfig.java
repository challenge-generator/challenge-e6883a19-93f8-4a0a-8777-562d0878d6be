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