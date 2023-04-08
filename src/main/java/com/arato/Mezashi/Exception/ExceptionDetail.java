package com.arato.Mezashi.Exception;

import java.time.LocalDateTime;

public class ExceptionDetail {
    private LocalDateTime timestamp;
    private String message;
    private String details;

    public ExceptionDetail(LocalDateTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return "ExceptionDetail{" +
                "timestamp=" + timestamp +
                ", message='" + message + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
