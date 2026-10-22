package com.example.url_shortener.service;

import com.example.url_shortener.dto.AnalyticsResponseDTO;
import com.example.url_shortener.dto.UrlRequestDTO;
import com.example.url_shortener.dto.UrlResponseDTO;
import com.example.url_shortener.entity.Url;
import com.example.url_shortener.exception.UrlExpiredException;
import com.example.url_shortener.exception.UrlNotFoundException;
import com.example.url_shortener.repository.UrlRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UrlService {
    private UrlRepository urlRepository;


    public UrlService(UrlRepository urlRepository){
        this.urlRepository = urlRepository;
    }

    public String generateShortCode(){
        String shortCode;
        do {
            UUID randomId = UUID.randomUUID();
            String uuid = randomId.toString();
            shortCode = uuid.substring(0, 6);
        }while(urlRepository.findByShortCode(shortCode)!= null);

        return shortCode;
    }

    public UrlResponseDTO saveUrl(String shortCode, String originalUrl, LocalDateTime expiresAt){
        Url urlEntity = urlRepository.findByOriginalUrl(originalUrl);
        if(urlEntity != null){
            return new UrlResponseDTO(
              urlEntity.getOriginalUrl(),
              urlEntity.getShortCode()

            );
        }
        Url existingShortCode = urlRepository.findByShortCode(shortCode);

        if (existingShortCode != null) {
            throw new RuntimeException("Custom alias already exists");
        }

        if (expiresAt != null && expiresAt.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Expiration time must be in the future.");
        }

        Url newUrl = new Url();
        newUrl.setShortCode(shortCode);
        newUrl.setOriginalUrl(originalUrl);
        newUrl.setExpiresAt(expiresAt);
        urlRepository.save(newUrl);

        return new UrlResponseDTO(
                newUrl.getOriginalUrl(),
                newUrl.getShortCode()
        );


    }

    public String getOriginalUrl(String shortCode){
       Url urlEntity = urlRepository.findByShortCode(shortCode);
       if(urlEntity == null){
           throw new UrlNotFoundException("Short Url not found");
       }
        if (urlEntity.getExpiresAt() != null &&
                urlEntity.getExpiresAt().isBefore(LocalDateTime.now())) {

            throw new UrlExpiredException("This URL has expired.");
        }
       urlEntity.setClickCount(
               urlEntity.getClickCount()+1
       );
       urlRepository.save(urlEntity);
       return urlEntity.getOriginalUrl();
    }


    public AnalyticsResponseDTO getAnalytics(String shortCode) {

        Url url = urlRepository.findByShortCode(shortCode);

        if (url == null) {
            throw new UrlNotFoundException("Short URL not found");
        }

        return new AnalyticsResponseDTO(
                url.getOriginalUrl(),
                url.getShortCode(),
                url.getClickCount()
        );
    }


}
