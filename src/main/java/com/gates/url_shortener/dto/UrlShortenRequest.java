package com.gates.url_shortener.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor  // ✅ Required for JSON deserialization
@Schema(description = "Request body for shortening a URL")
public class UrlShortenRequest {

    @Schema(description = "The long URL to be shortened", example = "https://example.com")
    private String longUrl;
}
