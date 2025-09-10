package com.erpsoftware.inv_sup_management.security.TokenService;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.erpsoftware.inv_sup_management.security.ApiException;

import jakarta.json.*;

@Service("JsonWebToken")
public class JWT implements TokenService{
    private Map<String, String> claims = new HashMap<>();
    private ArrayList<String> claimsKeys = new ArrayList<>();
    private long ExpInMiliSeconds = 0;

    public String verify(String token, String secret) throws UnsupportedEncodingException {
        if(!checkHeader(token)){
            throw new ApiException("Token algorithm mismatch", 401);
        }
        if (verifySignature(token, secret)) {
            String decodedClaims = decode(token,secret);
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
        } else {
            throw new ApiException("Invalid Token",401);
        }
    }

    @Override
    public String create(String data, String secret) throws Exception {
        String header = createHeader();
        String base64Header = Base64.getUrlEncoder().encodeToString(header.getBytes("UTF-8"));
        String claims = createClaims();
        JsonObject dataJson = Json.createReader(new StringReader(data)).readObject();
        JsonObject claimsJson = Json.createReader(new StringReader(claims)).readObject();
        JsonObjectBuilder builder = Json.createObjectBuilder();
        dataJson.forEach(builder::add);
        claimsJson.forEach(builder::add);
        JsonObject mergedClaims = builder.build();
        String payload = Encrypt(mergedClaims.toString());
        String signature = createSignature(base64Header + "." + payload, secret);
        String token = base64Header + "." + payload + "." + signature;
        return token;
    }

    @Override
    public String decode(String token, String secret) {
        try {
            if (token.isBlank())
                throw new ApiException("Unauthorized", 401);
            String[] tokenArr = token.split("\\.");
            if (tokenArr.length != 3) {
                throw new ApiException("Invalid token format, parts: " + tokenArr.length, 401);
            }
            String encryptedPayload = tokenArr[1];
            String decryptedPayload = Decrypt(encryptedPayload);
            try (JsonReader reader = Json.createReader(new StringReader(decryptedPayload))) {
                JsonObject jsonObj = reader.readObject();
                String payload = jsonObj.toString();
                return payload;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to decode token");
        }

    }

    private String createSignature(String payload, String secret) {
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

    private Boolean verifySignature(String token, String secret) {
        String[] tokenArr = token.split("\\.");
        String computedSignature = createSignature(tokenArr[0] + "." + tokenArr[1], secret);
        return computedSignature.equals(tokenArr[2]);
    }

    // this will create the header of jwt (algorithm and type)
    private String createHeader() {
        JsonObject json = Json.createObjectBuilder().add("alg", "HS256").add("typ", "jwt").build();
        return json.toString();
    }

    private Boolean checkHeader(String token) throws UnsupportedEncodingException{
        String header = token.split("\\.")[0];
        byte[] decodedHeader = Base64.getUrlDecoder().decode(header);
        String stringHeader = new String(decodedHeader,"UTF-8");
        return createHeader().equals(stringHeader);
    }

    // These are the jwt claims creation
    private String createClaims() {
        JsonObject json;
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("iat", String.valueOf(System.currentTimeMillis()));
        for (String key : claimsKeys) {
            String value = claims.get(key);
            builder.add(key, value);
        }
        builder.add("exp", String.valueOf(ExpInMiliSeconds));
        json = builder.build();
        return json.toString();
    }

    // subject claim
    @Override
    public JWT sub(String value) {
        claimsKeys.add("sub");
        claims.put("sub", value);
        return this;
    }

    // audience claim
    @Override
    public JWT aud(String value) {
        claimsKeys.add("aud");
        claims.put("aud", value);
        return this;
    }

    // not before claim (timestamp before which token is invalid || token can be use
    // after that duration)
    @Override
    public JWT nbf(String value) {
        claimsKeys.add("nbf");
        claims.put("nbf", String.valueOf(durationCalculator(value) + System.currentTimeMillis()));
        return this;
    }

    // jwt unique id claim
    @Override
    public JWT jti(String value) {
        claimsKeys.add("jti");
        claims.put("jti", value);
        return this;
    }

    // expire claim
    @Override
    public JWT exp(String value) {
        ExpInMiliSeconds += System.currentTimeMillis() + durationCalculator(value);
        return this;
    }

    // this is for duration of toke exp or others durations
    private long durationCalculator(String value) {
        String[] durations = value.split("\\:");
        long DurationInMil = 0;
        for (String duration : durations) {
            String[] time = duration.split("");
            int length = Integer.parseInt(time[0]);
            int hour = 3600000;
            int min = 60000;
            int sec = 1000;

            switch (time[1]) {
                // Month
                case "M":
                    DurationInMil += length * hour * 24 * 30;
                    break;
                // Week
                case "W":
                    DurationInMil += length * hour * 24 * 7;
                    break;
                // Day
                case "D":
                    DurationInMil += length * hour * 24;
                    break;
                // Hour
                case "h":
                    DurationInMil += length * hour;
                    break;
                // Minutes
                case "m":
                    DurationInMil += length * min;
                    break;
                // Second
                case "s":
                    DurationInMil += length * sec;
                    break;
                // Millisecond
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

    // This part is for encrypt and decrypt the payload using
    private String Encrypt(String payload) throws Exception {
        String base64String = Base64.getUrlEncoder().withoutPadding().encodeToString(payload.getBytes("UTF-8"));
        return base64String;
    }

    private String Decrypt(String payload) throws Exception {
        String decryptedString = new String(Base64.getUrlDecoder().decode(payload),"UTF-8");
        return decryptedString;
    }
}
