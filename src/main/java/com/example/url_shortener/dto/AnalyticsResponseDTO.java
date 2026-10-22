package com.example.url_shortener.dto;

public class AnalyticsResponseDTO {
    private String originalUrl;
    private String shortCode;
    private Integer clickCount;

    public AnalyticsResponseDTO() {
    }

    public AnalyticsResponseDTO(String originalUrl, String shortCode, Integer clickCount) {
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.clickCount = clickCount;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }
}
