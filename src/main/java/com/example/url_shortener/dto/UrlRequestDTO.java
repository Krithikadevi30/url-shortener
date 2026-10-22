package com.example.url_shortener.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class UrlRequestDTO {

    @NotBlank(message = "Original url cannot be blank")
    private String originalUrl;
    private LocalDateTime expiresAt;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    private String customAlias;

    public String getCustomAlias() {
        return customAlias;
    }

    public void setCustomAlias(String customAlias) {
        this.customAlias = customAlias;
    }



    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

}
