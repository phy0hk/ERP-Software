package com.erpsoftware.inv_sup_management.services;

import java.util.Set;
import java.util.HashSet;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

@Service
public class RedisManager {

    private final JedisPool pool;
    private final int ttlSeconds = 300; // 5 minutes

    public RedisManager() {
        this.pool = new JedisPool("localhost", 6379);
    }

    public void setData(String key, String value) {
        try (Jedis jedis = pool.getResource()) {
            jedis.setex(key, ttlSeconds, value);
        } catch (Exception e) {
            System.err.println("Failed to set data: " + e.getMessage());
        }
    }

    public String getData(String key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.get(key);
        } catch (Exception e) {
            System.err.println("Failed to get data: " + e.getMessage());
            return null;
        }
    }

   public void removeKeys(String pattern) {
    try (Jedis jedis = pool.getResource()) {
        Set<String> keysToDelete = new HashSet<>();
        String cursor = ScanParams.SCAN_POINTER_START; // usually "0"

        ScanParams scanParams = new ScanParams().match(pattern + ":*").count(100);

        do {
            ScanResult<String> scanResult = jedis.scan(cursor, scanParams);
            cursor = scanResult.getCursor();
            keysToDelete.addAll(scanResult.getResult());
        } while (!cursor.equals(ScanParams.SCAN_POINTER_START));

        if (!keysToDelete.isEmpty()) {
            jedis.del(keysToDelete.toArray(new String[0]));
        }
    } catch (Exception e) {
        System.err.println("Failed to remove keys: " + e.getMessage());
    }
}
}
