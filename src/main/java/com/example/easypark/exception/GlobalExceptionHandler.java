package com.example.easypark.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Regra de negócio
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleBusinessException(
            BusinessException ex,
            HttpServletRequest request
    ) {
        return new ApiError(
                409,
                "Business Error",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    // Erro inesperado
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleGenericException(
            Exception ex,
            HttpServletRequest request
    ) {
        return new ApiError(
                500,
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI()
        );
    }
}