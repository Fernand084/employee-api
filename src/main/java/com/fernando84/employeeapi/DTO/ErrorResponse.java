package com.fernando84.employeeapi.DTO;

import java.time.LocalDateTime;

public record ErrorResponse(
                LocalDateTime timestamp,
                int status,
                String error,
                String message,
                String path) {
}
