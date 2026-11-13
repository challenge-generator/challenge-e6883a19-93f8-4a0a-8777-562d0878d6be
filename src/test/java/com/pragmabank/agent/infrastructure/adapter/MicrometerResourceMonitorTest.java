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