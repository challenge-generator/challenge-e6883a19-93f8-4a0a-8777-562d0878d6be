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