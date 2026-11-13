package com.pragmabank.agent.infrastructure.adapter;


import com.pragmabank.agent.domain.model.TransactionStatus;
import com.pragmabank.agent.domain.model.Transaction;
import com.pragmabank.agent.domain.port.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaTransactionRepository implements TransactionRepository {

    private static final Logger logger = LoggerFactory.getLogger(JpaTransactionRepository.class);
    private final JpaRepository<Transaction, UUID> jpaRepository;

    public JpaTransactionRepository(JpaRepository<Transaction, UUID> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    @Transactional
    public Transaction save(Transaction transaction) {
        logger.debug("Guardando transacción con ID: {}", transaction.transactionId());
        Transaction saved = jpaRepository.save(transaction);
        logger.info("Transacción {} guardada exitosamente con estado: {}", 
                    saved.transactionId(), saved.status());
        return saved;
    }

    @Override
    public Optional<Transaction> findById(UUID id) {
        logger.debug("Buscando transacción por ID: {}", id);
        return jpaRepository.findById(id);
    }

    @Override
    public List<Transaction> findByStatus(Transaction.TransactionStatus status) {
        logger.debug("Buscando transacciones con estado: {}", status);
        return jpaRepository.findAll().stream()
                .filter(t -> t.status().equals(status))
                .toList();
    }

    @Override
    @Transactional
    @Modifying
    public Transaction updateStatus(UUID id, Transaction.TransactionStatus newStatus, String metadata) {
        logger.info("Actualizando estado de transacción {} a {}", id, newStatus);
        Optional<Transaction> existingOpt = jpaRepository.findById(id);
        
        if (existingOpt.isEmpty()) {
            logger.error("No se encontró la transacción con ID: {}", id);
            throw new IllegalArgumentException("Transacción no encontrada: " + id);
        }
        
        Transaction existing = existingOpt.get();
        Transaction updated = switch (newStatus) {
            case PROCESSED -> existing.markAsProcessed(metadata);
            case FAILED -> existing.markAsFailed(metadata);
            default -> existing;
        };
        
        return jpaRepository.save(updated);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        logger.info("Eliminando transacción con ID: {}", id);
        if (!jpaRepository.existsById(id)) {
            logger.warn("Intento de eliminar transacción inexistente: {}", id);
            throw new IllegalArgumentException("Transacción no encontrada para eliminar: " + id);
        }
        jpaRepository.deleteById(id);
    }

    @Override
    public long countByStatus(Transaction.TransactionStatus status) {
        logger.debug("Contando transacciones con estado: {}", status);
        return jpaRepository.findAll().stream()
                .filter(t -> t.status().equals(status))
                .count();
    }
}