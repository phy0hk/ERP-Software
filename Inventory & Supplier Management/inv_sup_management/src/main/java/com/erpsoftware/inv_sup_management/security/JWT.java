package com.erpsoftware.inv_sup_management.security;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import jakarta.json.*;

public class JWT {
    private Map<String, String> claims = new HashMap<>();
    private ArrayList<String> claimsKeys = new ArrayList<>();
    private long ExpInMiliSeconds = 0;

    public String verify(String token, String secret) {
        if (verifySignature(token, secret)) {
            String decodedClaims = getClaims(token);
            try (JsonReader reader = Json.createReader(new StringReader(decodedClaims))) {
                JsonObject jsonObj = reader.readObject();
                String expStr = jsonObj.getString("exp");
                Long exp = Long.parseLong(expStr);
                Long now = System.currentTimeMillis();
                if (jsonObj.containsKey("nbf")&&!jsonObj.isNull("nbf")) {
                    String nbfStr = jsonObj.getString("nbf");
            long nbf = Long.parseLong(nbfStr);
                    if (nbf > now) {
                        return "Token can't be use until certail time reach";
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
            return "Invalid Token";
        }
    }

    public String create(String data, String secret) throws Exception {
        String header = createHeader();
        String base64Header = Base64.getEncoder().encodeToString(header.getBytes("UTF-8"));
        String claimsBody = createClaims();
        JsonObject payloadJson = Json.createObjectBuilder().add("data", data).add("claims", claimsBody).build();
        String payload = Encrypt(payloadJson.toString());
        String signature = createSignature(base64Header + "." + payload, secret);
        String token = base64Header + "." + payload + "." + signature;
        return token;
    }

    public String decode(String token, String secret) {
        try {
            if (token.isBlank())
                throw new ApiAuthException("Unauthorized", 401);
            String[] tokenArr = token.split("\\.");
            if (tokenArr.length != 3) {
                throw new ApiAuthException("Invalid token format, parts: " + tokenArr.length, 401);
            }
            String encryptedPayload = tokenArr[1];
            String decryptedPayload = Decrypt(encryptedPayload);
            try (JsonReader reader = Json.createReader(new StringReader(decryptedPayload))) {
                JsonObject jsonObj = reader.readObject();
                String payload = jsonObj.getString("data");
                return payload;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to decode token");
        }

    }

    public String getClaims(String token) {
        if (token.isBlank())
            throw new ApiAuthException("Token does not exist", 401);
        try {
            String[] tokenArr = token.split("\\.");
            if (tokenArr.length != 3) {
                throw new ApiAuthException("Invalid token format, parts: " + tokenArr.length, 401);
            }
            String encryptedPayload = tokenArr[1];
            String decryptedPayload = Decrypt(encryptedPayload);
            try (JsonReader reader = Json.createReader(new StringReader(decryptedPayload))) {
                JsonObject jsonObj = reader.readObject();
                String payload = jsonObj.getString("claims");
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
        JsonObject json = Json.createObjectBuilder().add("alg", "AES").add("typ", "jwt").build();
        return json.toString();
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
    public JWT sub(String key, String value) {
        claims.put(key, value);
        return this;
    }

    // audience claim
    public JWT aud(String key, String value) {
        claims.put(key, value);
        return this;
    }

    // not before claim (timestamp before which token is invalid || token can be use
    // after that duration)
    public JWT nbf(String key, String value) {
        claimsKeys.add(key);
        claims.put(key, String.valueOf(durationCalculator(value) + System.currentTimeMillis()));
        return this;
    }

    // jwt unique id claim
    public JWT jti(String key, String value) {
        claimsKeys.add(key);
        claims.put(key, value);
        return this;
    }

    // expire claim
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
        String base64String = Base64.getEncoder().encodeToString(payload.getBytes("UTF-8"));
        return base64String;
    }

    private String Decrypt(String payload) throws Exception {
        String decryptedString = new String(Base64.getDecoder().decode(payload),"UTF-8");
        return decryptedString;
    }
}
