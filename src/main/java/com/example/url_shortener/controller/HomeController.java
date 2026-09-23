package com.example.url_shortener.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

@RestController
public class HomeController {
    HashMap<String,String> urlMap = new HashMap<>();


    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to URL Shortener !!");
    }


    @GetMapping("/shortener")
    public String shortener(@RequestParam String url) {

        UUID randomId = UUID.randomUUID();
        String uuid = randomId.toString();
        String shortCode = uuid.substring(0,6);

        urlMap.put(shortCode,url);
        System.out.println(urlMap);

        return "You entered:\n" + url
                +"\n Status: \n" + "Received Successfully!"
                +"\n Short Code: \n" + shortCode;
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode ){
        String originalUrl = urlMap.get(shortCode);
        if(originalUrl == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION,originalUrl)
                .build();
    }


}