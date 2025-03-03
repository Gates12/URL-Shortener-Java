package com.gates.url_shortener.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UrlShortenerService {

    private final ConcurrentHashMap<String, Integer> accessCounts = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> expiryTimes = new ConcurrentHashMap<>();
    private static final long EXPIRATION_TIME_MS = 24 * 60 * 60 * 1000; // 24 hours

    public boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void trackAccess(String shortKey) {
        accessCounts.put(shortKey, accessCounts.getOrDefault(shortKey, 0) + 1);
    }

    public int getAccessCount(String shortKey) {
        return accessCounts.getOrDefault(shortKey, 0);
    }

    public void setExpiration(String shortKey) {
        expiryTimes.put(shortKey, System.currentTimeMillis() + EXPIRATION_TIME_MS);
    }

    public boolean isExpired(String shortKey) {
        return expiryTimes.containsKey(shortKey) && System.currentTimeMillis() > expiryTimes.get(shortKey);
    }

    @Scheduled(fixedRate = 90000) // Runs every 90 seconds to clean expired URLs
    public void cleanExpiredUrls() {
        expiryTimes.keySet().removeIf(this::isExpired);
    }
}
