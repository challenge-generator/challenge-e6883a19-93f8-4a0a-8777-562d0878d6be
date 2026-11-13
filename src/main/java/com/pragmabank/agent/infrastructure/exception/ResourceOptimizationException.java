package com.pragmabank.agent.infrastructure.exception;

public class ResourceOptimizationException extends RuntimeException {

    private final String errorCode;
    private final String details;

    public ResourceOptimizationException(String message) {
        super(message);
        this.errorCode = "RESOURCE_OPTIMIZATION_ERROR";
        this.details = null;
    }

    public ResourceOptimizationException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "RESOURCE_OPTIMIZATION_ERROR";
        this.details = null;
    }

    public ResourceOptimizationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.details = null;
    }

    public ResourceOptimizationException(String message, String errorCode, String details) {
        super(message);
        this.errorCode = errorCode;
        this.details = details;
    }

    public ResourceOptimizationException(String message, String errorCode, String details, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.details = details;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ResourceOptimizationException{");
        sb.append("errorCode='").append(errorCode).append('\'');
        sb.append(", message='").append(getMessage()).append('\'');
        if (details != null) {
            sb.append(", details='").append(details).append('\'');
        }
        sb.append('}');
        return sb.toString();
    }
}