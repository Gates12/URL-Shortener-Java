package com.gates.url_shortener;

import com.gates.url_shortener.controller.UrlShortenerController;
import com.gates.url_shortener.dto.UrlShortenRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.gates.url_shortener")  // ✅ Ensures all sub-packages are scanned
public class UrlShortenerApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(UrlShortenerApplication.class, args);

		// ✅ Correct way to get a Spring-managed bean
		UrlShortenerController controller = context.getBean(UrlShortenerController.class);
		controller.shortenUrl(new UrlShortenRequest("https://example.com")); // Just to remove the warning
	}
}

