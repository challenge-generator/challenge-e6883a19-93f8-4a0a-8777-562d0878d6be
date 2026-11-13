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