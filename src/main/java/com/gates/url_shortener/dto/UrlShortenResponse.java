package com.gates.url_shortener.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor  // ✅ Required for response serialization
@Schema(description = "Response body for shortened URL")
public class UrlShortenResponse {

    @Schema(description = "The shortened URL", example = "http://localhost:8080/api/r/abc123")
    private String shortUrl;
}
