package com.erpsoftware.inv_sup_management.responseModel;

public class ResponseJson {
    public record StatusResponder<T>(String status, T data) {}
}
