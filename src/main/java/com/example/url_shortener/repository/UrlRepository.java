package com.example.url_shortener.repository;

import com.example.url_shortener.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url,Long> {

    Url findByShortCode(String shortcode);

    Url findByOriginalUrl(String originalUrl);


}
