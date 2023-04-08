package com.arato.Mezashi.Exception;

import com.arato.Mezashi.Mezashi.exception.CannotDeleteException;
import com.arato.Mezashi.User.exception.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class ResponseEntityExceptionHandler extends org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionDetail> handleAllExceptions(Exception ex, WebRequest req) throws Exception {
        ExceptionDetail details = new ExceptionDetail(
                LocalDateTime.now(),
                ex.getMessage(),
                req.getDescription(false)
        );
        return new ResponseEntity<>(details, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CannotDeleteException.class)
    public ResponseEntity<ExceptionDetail> handleCannotDeleteException(Exception ex, WebRequest request) throws Exception {
        ExceptionDetail details = new ExceptionDetail(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionDetail> handleUserNotFoundException(Exception ex, WebRequest request) throws Exception {
        ExceptionDetail detail = new ExceptionDetail(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(detail, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        ExceptionDetail detail = new ExceptionDetail(
                LocalDateTime.now(),
                "Total Errors: " + ex.getFieldErrorCount() + "; First Error: " + ex.getFieldError().getDefaultMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<Object>(detail, HttpStatus.BAD_REQUEST);
    }

}
