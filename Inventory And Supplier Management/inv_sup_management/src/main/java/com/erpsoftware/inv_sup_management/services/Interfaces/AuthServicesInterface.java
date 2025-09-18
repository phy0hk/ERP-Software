package com.erpsoftware.inv_sup_management.services.Interfaces;

import org.springframework.stereotype.Service;

@Service
public interface AuthServicesInterface {
    Boolean Login(String email,String password);
    String getToken(String email);
    boolean Register(String email,String password);
    String CheckAuth();
}
