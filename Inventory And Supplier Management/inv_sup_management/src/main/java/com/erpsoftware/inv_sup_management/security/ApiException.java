package com.erpsoftware.inv_sup_management.security;

public class ApiException extends RuntimeException {
        private final int status;
        public ApiException(String message,int status){
            super(message);
            this.status = status;
        }
        public int getStatus() {
            return status;
        }
}
