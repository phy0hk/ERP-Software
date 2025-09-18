package com.erpsoftware.inv_sup_management.security.TokenService;

public interface TokenService {
    //main feature
    String verify(String token,String secret) throws Exception;
    String create(String payload,String secret) throws Exception;
    String decode(String token,String secret);

    //Claims
    TokenService exp(String exp);
    TokenService jti(String value);
    TokenService nbf(String value);
    TokenService aud(String value);
    TokenService sub(String value);
}
