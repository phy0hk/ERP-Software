package com.erpsoftware.inv_sup_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.erpsoftware.inv_sup_management.security.ApiAuthException;
import com.erpsoftware.inv_sup_management.security.TokenService.TokenService;
import com.erpsoftware.inv_sup_management.services.Interfaces.AuthServicesInterface;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthServices implements AuthServicesInterface{

    private String admEmail = "admin@email.com";
    private String admPassword = "Admin123";
    
    @Autowired
    private TokenService tokenService;

    @Value("${SECRET_KEY}")
    private String Secret;
    
    @Autowired
    HttpServletRequest request;

    @Override
    public Boolean Login(String email, String password) {
        if(email.equals(admEmail) && password.equals(admPassword)){
            return true;
        }
        return false;
    }

    @Override
    public String getToken(String email) {
        JsonObject json = Json.createObjectBuilder().add("email", admEmail).add("password", admPassword).build();
        try {
            String token = tokenService.create(json.toString(), Secret);
            return token;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create token");
        }
    }

    @Override
    public boolean Register(String email, String password) {
        throw new UnsupportedOperationException("Unimplemented method 'Register'");
    }

    @Override
    public String CheckAuth() {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if(cookies != null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("token")){
                    token = cookie.getValue();
                }
            }
        }
        if(token!=null){
            try {
                return tokenService.verify(token, Secret);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new ApiAuthException("Unauthorized", 401);
    }
    
}
