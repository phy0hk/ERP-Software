package com.erpsoftware.inv_sup_management.security;

public class ApiAuthException extends RuntimeException {
        private final int status;
        public ApiAuthException(String message,int status){
            super(message);
            this.status = status;
        }
        public int getStatus() {
            return status;
        }
}
