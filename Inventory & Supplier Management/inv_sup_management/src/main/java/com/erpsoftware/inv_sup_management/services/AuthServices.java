package com.erpsoftware.inv_sup_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.erpsoftware.inv_sup_management.security.ApiAuthException;
import com.erpsoftware.inv_sup_management.security.JWT;
import com.erpsoftware.inv_sup_management.services.Interfaces.AuthServicesInterface;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthServices implements AuthServicesInterface{

    private String admEmail = "admin@email.com";
    private String admPassword = "Admin123";
    private JWT jwt = new JWT();

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
            String token = jwt.exp("1m").create(json.toString(), Secret);
            System.out.println(jwt.getClaims(token, Secret));
            System.out.println(jwt.decode(token, Secret));
            return token;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create token");
        }
    }

    @Override
    public boolean Register(String email, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Register'");
    }

    @Override
    public boolean CheckAuth() {
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
            System.out.println(jwt.getClaims(token, Secret));
            return jwt.verify(token, Secret);
        }
        throw new ApiAuthException("Unauthorized", 401);
    }
    
}
