package com.erpsoftware.inv_sup_management.services;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component

public class AuthService {
    @Value("${jwt.secret.key}")
    private String secretKey;
    public boolean preHandler(HttpServletRequest req,HttpServletResponse res,Object handler) throws Exception{
        Cookie[] cookies = req.getCookies();
        String token = null;
        if(cookies != null){
            for(var cookie: req.getCookies()){
                if("AUTH_TOKEN".equals(cookie.getName())){
                    token = cookie.getValue();
                }
            }
        }
        if (token == null || !token.equals("my-secret-token")) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Unauthorized");
            return false;
        }

        return false;
    }

    public boolean verify(String token){
        return true;
    }
    public String createToken(Object obj){

        return "";
    }
}