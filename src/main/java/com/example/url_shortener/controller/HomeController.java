package com.example.url_shortener.controller;
import com.example.url_shortener.dto.AnalyticsResponseDTO;
import com.example.url_shortener.dto.UrlResponseDTO;
import com.example.url_shortener.dto.UrlRequestDTO;
import com.example.url_shortener.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/url")
public class HomeController {
    private UrlService urlService;

    public HomeController(UrlService urlService){
        this.urlService = urlService;
    }


    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to URL Shortener !!");
    }


    @PostMapping("/shorten")
    public UrlResponseDTO shortener(@Valid @RequestBody UrlRequestDTO request) {

        String originalUrl = request.getOriginalUrl();

        String shortCode;

        if (request.getCustomAlias() != null &&
                !request.getCustomAlias().isBlank()) {

            shortCode = request.getCustomAlias();

        } else {

            shortCode = urlService.generateShortCode();

        }

        UrlResponseDTO response =
                urlService.saveUrl(
                        shortCode,
                        originalUrl,
                        request.getExpiresAt()
                );


        response.setShortUrl("http://localhost:8080/"+ response.getShortCode());

        return response;
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode ){
        String originalUrl = urlService.getOriginalUrl(shortCode);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION,originalUrl)
                .build();
    }

    @GetMapping("/analytics/{shortCode}")
    public ResponseEntity<AnalyticsResponseDTO> getAnalytics(
            @PathVariable String shortCode) {

        return ResponseEntity.ok(
                urlService.getAnalytics(shortCode)
        );
    }


}