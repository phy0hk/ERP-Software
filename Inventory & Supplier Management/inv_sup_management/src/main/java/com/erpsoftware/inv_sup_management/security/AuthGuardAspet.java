package com.erpsoftware.inv_sup_management.security;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuthGuardAspet {

    @Value("${SECRET_KEY}")
    private String Secret;

    @Autowired
    private HttpServletRequest request;

    @Before("@within(AuthGuard) ||@annotation(AuthGuard)")
    public void checkAuth(JoinPoint joinPoint) throws Exception {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        AuthGuard authGuard = method.getAnnotation(AuthGuard.class);
        if (authGuard == null) {
            authGuard = joinPoint.getTarget().getClass().getAnnotation(AuthGuard.class);
        }

        if (authGuard != null) {
            validateRequest(authGuard);
        }
    }

    private void validateRequest(AuthGuard authGuard) throws Exception {
        Cookie[] cookies = request.getCookies();
        JWT jwt = new JWT();
        String token = null;
        if(cookies != null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("token")){
                    token = cookie.getValue();
                }
            }
        }
        if (token == null) {
            String p1 = new Product("Coffee").toString();
            String Jwttoken = jwt.create(p1, Secret);
            System.out.println(jwt.getClaims(Jwttoken, Secret));
            System.out.println(Jwttoken);
            System.out.println(jwt.decode(Jwttoken, Secret));

            throw new ApiAuthException("Unauthorized", 401);
        } else {

        }
    }

    public class Product{

        JsonObject json;
        public Product(String name){
            json = Json.createObjectBuilder().add("name", name).build();
        }
        public void setName(String name) {
            json = Json.createObjectBuilder().add("name", name).build();
        }
        public String getName() {
            return json.toString();
        }
        @Override
        public String toString() {
            return json.toString();
        }
    }
}

