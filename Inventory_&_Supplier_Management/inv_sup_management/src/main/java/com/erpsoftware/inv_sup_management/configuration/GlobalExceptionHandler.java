package com.erpsoftware.inv_sup_management.configuration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.erpsoftware.inv_sup_management.security.ApiAuthException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiAuthException.class)
    public ResponseEntity<ErrorResponse> handleApiAuthException(ApiAuthException ex) {
        return ResponseEntity
                .status(ex.getStatus()) // 401 or 403
                .body(new ErrorResponse(ex.getMessage(), ex.getStatus()));
    }

     @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return ResponseEntity
                .status(500)
                .body(new ErrorResponse(ex.getMessage(), 500));
    }


    public static class ErrorResponse{
        private String error;
        private int status;
        
        public ErrorResponse(String error,int status){
            this.error = error;
            this.status = status;
        }
        
        public String getError() {return error;}
        public int getStatus() {return status;}
    }
}
