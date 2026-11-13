package com.pragmabank.agent.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad del dominio que representa una transacción financiera.
 * Contiene los atributos esenciales de una transacción y su estado.
 */
public record Transaction(
    UUID id,
    BigDecimal amount,
    String currency,
    String description,
    LocalDateTime timestamp,
    TransactionStatus status,
    String metadata) {

    /**
     * Enum que representa los posibles estados de una transacción.
     */
    public enum TransactionStatus {
        PENDING,
        PROCESSED,
        FAILED,
        ROLLED_BACK
    }

    /**
     * Constructor que valida los campos requeridos antes de crear la transacción.
     * @param id Identificador único de la transacción
     * @param amount Monto de la transacción
     * @param currency Código de moneda (ej. USD, EUR)
     * @param description Descripción de la transacción
     * @param timestamp Fecha y hora de creación
     * @param status Estado actual de la transacción
     * @param metadata Información adicional en formato JSON
     * @throws IllegalArgumentException si algún campo requerido es nulo o inválido
     */
    public Transaction {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la transacción no puede ser nulo");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("La moneda no puede estar vacía");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("La fecha y hora no pueden ser nulas");
        }
        if (status == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }
    }

    /**
     * Crea una nueva transacción con estado PENDING.
     * @param amount Monto de la transacción
     * @param currency Código de moneda
     * @param description Descripción de la transacción
     * @return Nueva instancia de Transaction con estado PENDING
     */
    public static Transaction createPending(BigDecimal amount, String currency, String description) {
        return new Transaction(
            UUID.randomUUID(),
            amount,
            currency,
            description,
            LocalDateTime.now(),
            TransactionStatus.PENDING,
            "{}"
        );
    }

    /**
     * Marca la transacción como PROCESSED y actualiza su metadata.
     * @param newMetadata Metadata adicional en formato JSON
     * @return Nueva instancia con el estado actualizado
     */
    public Transaction markAsProcessed(String newMetadata) {
        return new Transaction(
            this.id,
            this.amount,
            this.currency,
            this.description,
            this.timestamp,
            TransactionStatus.PROCESSED,
            newMetadata
        );
    }

    /**
     * Marca la transacción como FAILED y actualiza su metadata con la causa.
     * @param errorCause Causa del fallo
     * @return Nueva instancia con el estado actualizado
     */
    public Transaction markAsFailed(String errorCause) {
        return new Transaction(
            this.id,
            this.amount,
            this.currency,
            this.description,
            this.timestamp,
            TransactionStatus.FAILED,
            String.format("{ \"error\": \"%s\" }", errorCause)
        );
    }
}