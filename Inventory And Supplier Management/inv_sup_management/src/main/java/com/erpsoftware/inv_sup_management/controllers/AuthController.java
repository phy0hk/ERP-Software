package com.erpsoftware.inv_sup_management.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erpsoftware.inv_sup_management.security.AuthGuardAspet;
import com.erpsoftware.inv_sup_management.services.AuthServices;
import com.erpsoftware.inv_sup_management.services.Interfaces.AuthServicesInterface;
import com.erpsoftware.inv_sup_management.utils.ResponseJson.StatusResponder;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthServices authServices;

    private final AuthGuardAspet authGuardAspet;

    private final  AuthServicesInterface authService;

    public AuthController(AuthServicesInterface authService, AuthGuardAspet authGuardAspet, AuthServices authServices){
        this.authService = authService;
        this.authGuardAspet = authGuardAspet;
        this.authServices = authServices;
    }

    @PostMapping("/login")
    public StatusResponder<String> Login(@RequestBody LoginForm body,HttpServletResponse response) {
        String email = body.email;
        String password = body.password;
        if(authService.Login(email,password)){
            String token = authService.getToken(email);
            Cookie cookie = new Cookie("token",token);
            cookie.setHttpOnly(true);         
            cookie.setSecure(true);            
            cookie.setPath("/");              
            cookie.setMaxAge(60 * 60);
            response.addCookie(cookie);
            return new StatusResponder<>("200", "Login Success");
        }else{
            return new StatusResponder<>("401","Incorrect email or password");
        }
    }

    @RequestMapping("/logout")
    public StatusResponder<String> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null); // same name
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        return new StatusResponder<>("ok","Logged out successfully");
    }

    @RequestMapping("/check")
    public StatusResponder<String> check(){
        String checkAuthRes = authServices.CheckAuth();
        if(checkAuthRes.equals("ok")){
            return new StatusResponder<>("ok","Authorized");
        }
        return new StatusResponder<>("401", checkAuthRes);
    }
    

    public record LoginForm(String email,String password){};
}
