package com.erpsoftware.inv_sup_management.security.TokenService;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.erpsoftware.inv_sup_management.security.ApiAuthException;

import jakarta.json.*;

@Service("SecureJsonToken")
@Primary
public class SJT implements TokenService{
    private byte[] iv;
    private Map<String,String> claims = new HashMap<>();
    private ArrayList<String> claimsKeys = new ArrayList<>();
    private long ExpInMiliSeconds = 0;

    public void CreateRandomBytes(){
        iv = new byte[12];
        new SecureRandom().nextBytes(iv);
    }

    public byte[] decodeIv(String ivString){
        byte[] base64Byte = Base64.getUrlDecoder().decode(ivString);
        return base64Byte;
    }
    
    @Override
    public String verify(String token,String secret) throws Exception{
        if(!checkHeader(token)){
            throw new ApiAuthException("Token algorithm mismatch", 401);
        }
        if(verifySignature(token, secret)){
          String decodedClaims = getClaims(token, secret);
            try (JsonReader reader = Json.createReader(new StringReader(decodedClaims))) {
                JsonObject jsonObj = reader.readObject();
                String expStr = jsonObj.getString("exp");
                Long exp = Long.parseLong(expStr);
                Long now = System.currentTimeMillis();
                if (jsonObj.containsKey("nbf")&&!jsonObj.isNull("nbf")) {
                    String nbfStr = jsonObj.getString("nbf");
            long nbf = Long.parseLong(nbfStr);
                    if (nbf > now) {
                        return "Token is not yet valid";
                    }
                }
                if (exp == 0) {
                    return "ok";
                } else if (now > exp) {
                    return "Token Expired";
                } else {
                    return "ok";
                }
            }
        }else{
            throw new ApiAuthException("Invalid Token", 401);
        }
    }

    @Override
    public String create(String data,String secret) throws Exception{
        CreateRandomBytes();
        String header = createHeader();
        String base64Header = Base64.getUrlEncoder().withoutPadding().encodeToString(header.getBytes("UTF-8"));
        String claimsBody = createClaims();
        JsonObject payloadJson = Json.createObjectBuilder().add("data", data).add("claims", claimsBody).build();
        String payload = Encrypt(payloadJson.toString(), secret);
        String base64iv = Base64.getUrlEncoder().withoutPadding().encodeToString(iv);
        String signature = createSignature(base64Header+"."+base64iv+"."+payload, secret);
        String token = base64Header+"."+base64iv+"."+payload+"."+signature;
        return token;
    }

    @Override
    public String decode(String token,String secret){
        try {
            if(token.isBlank()) throw new ApiAuthException("Unauthorized", 401);
            String[] tokenArr = token.split("\\.");
            if (tokenArr.length != 4) {
    throw new ApiAuthException("Invalid token format, parts: " + tokenArr.length,401);
}
            String encryptedPayload = tokenArr[2];
            String ivString = tokenArr[1];
            String decryptedPayload = Decrypt(encryptedPayload,ivString, secret);
            try(JsonReader reader = Json.createReader(new StringReader(decryptedPayload))){
                JsonObject jsonObj = reader.readObject();
                String payload = jsonObj.getString("data");
                return payload;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to decode token");
        }
        
    }
    private String getClaims(String token,String secret){
        if(token.isBlank()) throw new ApiAuthException("Unauthorized", 401);
        try {
            String[] tokenArr = token.split("\\.");
            if (tokenArr.length != 4) {
    throw new ApiAuthException("Invalid token format, parts: " + tokenArr.length,401);
}
            String encryptedPayload = tokenArr[2];
            String ivString = tokenArr[1];
            String decryptedPayload = Decrypt(encryptedPayload,ivString, secret);
            try(JsonReader reader = Json.createReader(new StringReader(decryptedPayload))){
                JsonObject jsonObj = reader.readObject();
                String payload = jsonObj.getString("claims");
                return payload;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to decode token");
        }
        
    }


    private String createSignature(String payload,String secret){
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secretKey);
            byte[] payloadBytes = payload.getBytes("UTF-8");
            byte[] hash = sha256_HMAC.doFinal(payloadBytes);
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);  
        } catch (Exception e) {
            throw new RuntimeException("Failed to encode HMAC");
        }
    }

    private Boolean checkHeader(String token) throws UnsupportedEncodingException{
        String header = token.split("\\.")[0];
        byte[] decodedHeader = Base64.getUrlDecoder().decode(header);
        String stringHeader = new String(decodedHeader,"UTF-8");
        return createHeader().equals(stringHeader);
    }

    private Boolean verifySignature(String token,String secret){
        String[] tokenArr = token.split("\\.");
        String computedSignature = createSignature(tokenArr[0]+"."+tokenArr[1]+"."+tokenArr[2], secret);
        return computedSignature.equals(tokenArr[3]);
    }

    //this will create the header of jwt (algorithm and type)
    private String createHeader(){
        JsonObject json = Json.createObjectBuilder().add("alg", "AES").add("typ", "jst").build();
        return json.toString();
    }



    //These are the jwt claims creation
    private String createClaims(){
        JsonObject json;
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("iat", String.valueOf(System.currentTimeMillis()));
        for(String key:claimsKeys){
            String value = claims.get(key);
            builder.add(key, value);
        }
        builder.add("exp", String.valueOf(ExpInMiliSeconds));
        json = builder.build();
        return json.toString();
    }

    //subject claim
    @Override
    public SJT sub(String value){
        claimsKeys.add("sub");
        claims.put("sub", value);
        return this;
    }

    //audience claim
    @Override
    public SJT aud(String value){
        claimsKeys.add("aud");
        claims.put("aud", value);
        return this;
    }

    //not before claim (timestamp before which token is invalid || token can be use after that duration)
    @Override
    public SJT nbf(String value){
        claimsKeys.add("nbf");
        claims.put("nbf", String.valueOf(durationCalculator(value)+System.currentTimeMillis()));
        return this;
    }

    //jwt unique id claim
    @Override
    public SJT jti(String value){
        claimsKeys.add("jti");
        claims.put("jti", value);
        return this;
    }

    //expire claim
    @Override
    public SJT exp(String value){
        ExpInMiliSeconds += System.currentTimeMillis()+durationCalculator(value);
        return this;
    }


    //this is for duration of toke exp or others durations
    private long durationCalculator(String value){
        String[] durations = value.split("\\:");
        long DurationInMil = 0;
        for(String duration:durations){
            String[] time = duration.split("");
            int length = Integer.parseInt(time[0]);
            int hour = 3600000;
            int min = 60000;
            int sec = 1000;
            
            switch (time[1]) {
                //Month
                case "M":
                    DurationInMil += length*hour*24*30;
                    break;
                //Week
                case "W":
                    DurationInMil += length*hour*24*7;
                    break;
                //Day
                case "D":
                    DurationInMil += length*hour*24;
                    break;
                //Hour
                case "h":
                    DurationInMil += length*hour;
                    break;
                //Minutes
                case "m":
                    DurationInMil += length*min;
                    break;
                //Second
                case "s":
                    DurationInMil += length*sec;
                    break;
                //Millisecond
                case "ms":
                    DurationInMil += length;
                    break;
                default:
                DurationInMil += length;
                    break;
            }
        }
        return DurationInMil;
    }


    //this is to convert normal secret key string to SecretKeySpec that can be use in encrypt and decrypt
    private SecretKeySpec getKeyFromString(String secret) throws Exception{
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha.digest(secret.getBytes("UTF-8"));
        return new SecretKeySpec(keyBytes,"AES");
    }

    //This part is for encrypt and decrypt the payload using aes algo
    private String Encrypt(String payload,String secret)throws Exception{
        SecretKeySpec key = getKeyFromString(secret);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key,spec);
        byte[] encrypt = cipher.doFinal(payload.getBytes("UTF-8"));
        return Base64.getUrlEncoder().withoutPadding().encodeToString(encrypt);
    }

    private String Decrypt(String payload,String ivString,String secret) throws Exception{
        SecretKeySpec key = getKeyFromString(secret);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        this.iv = decodeIv(ivString);
        GCMParameterSpec spec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key,spec);
        byte[] decode = Base64.getUrlDecoder().decode(payload);
        return new String(cipher.doFinal(decode),"UTF-8");
    }
}

