package com.gates.url_shortener.controller;

import com.gates.url_shortener.dto.UrlShortenRequest;
import com.gates.url_shortener.dto.UrlShortenResponse;
import com.gates.url_shortener.service.UrlShortenerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api")
@Tag(name = "URL Shortener", description = "API for shortening and retrieving URLs")
public class UrlShortenerController {

    private final Map<String, String> urlMap = new ConcurrentHashMap<>();
    private final Map<String, String> reverseUrlMap = new ConcurrentHashMap<>(); // To ensure same short URL for same long URL
    private static final String BASE_URL = "http://localhost:8080/api/r/";

    @Autowired
    private UrlShortenerService urlShortenerService;

    @PostMapping("/shorten")
    @Operation(summary = "Shorten a URL", description = "Converts a long URL into a shortened version.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully shortened the URL"),
            @ApiResponse(responseCode = "400", description = "Invalid URL")
    })
    public ResponseEntity<UrlShortenResponse> shortenUrl(@RequestBody UrlShortenRequest request) {
        String longUrl = request.getLongUrl();

        // Validate URL
        if (longUrl == null || longUrl.isBlank() || !urlShortenerService.isValidUrl(longUrl)) {
            return ResponseEntity.badRequest().body(new UrlShortenResponse("❌ Invalid URL"));
        }

        // Check if URL is already shortened
        if (reverseUrlMap.containsKey(longUrl)) {
            return ResponseEntity.ok(new UrlShortenResponse(BASE_URL + reverseUrlMap.get(longUrl)));
        }

        // Generate a unique short key
        String shortKey;
        do {
            shortKey = generateShortKey();
        } while (urlMap.containsKey(shortKey)); // Ensure unique key

        // Store mappings
        urlMap.put(shortKey, longUrl);
        reverseUrlMap.put(longUrl, shortKey);

        // Set expiration
        urlShortenerService.setExpiration(shortKey);

        return ResponseEntity.ok(new UrlShortenResponse(BASE_URL + shortKey));
    }

    @GetMapping("/r/{shortKey}")
    @Operation(summary = "Redirect to original URL", description = "Redirects a shortened URL back to the original URL.")
    @ApiResponses({
            @ApiResponse(responseCode = "302", description = "Redirecting to original URL"),
            @ApiResponse(responseCode = "404", description = "Short URL not found")
    })
    public ResponseEntity<Void> redirectToOriginal(@PathVariable String shortKey) {
        String longUrl = urlMap.get(shortKey);
        if (longUrl == null) {
            return ResponseEntity.notFound().build();
        }
        urlShortenerService.trackAccess(shortKey);
        return ResponseEntity.status(302).header("Location", longUrl).build();
    }


    @GetMapping("/access-count/{shortKey}")
    @Operation(summary = "Get Access Count", description = "Returns the number of times a short URL has been accessed.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Access count retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Short URL not found")
    })
    public ResponseEntity<Map<String, Integer>> getAccessCount(@PathVariable String shortKey) {
        if (!urlMap.containsKey(shortKey)) {
            return ResponseEntity.notFound().build();
        }

        int count = urlShortenerService.getAccessCount(shortKey);
        Map<String, Integer> response = new ConcurrentHashMap<>();
        response.put("accessCount", count);

        return ResponseEntity.ok(response);
    }

    private String generateShortKey() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder shortKey = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            shortKey.append(characters.charAt(random.nextInt(characters.length())));
        }
        return shortKey.toString();
    }
}
