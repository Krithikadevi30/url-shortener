package com.example.url_shortener.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

@RestController
public class HomeController {
    HashMap<String,String> urlmap = new HashMap<>();

    @GetMapping("/")
    public String home() {
        return "Welcome to URL Shortener !!";
    }


    @GetMapping("/shortener")
    public String shortener(@RequestParam String url) {

        UUID randomId = UUID.randomUUID();
        String uuid = randomId.toString();
        String shortCode = uuid.substring(0,6);

        return "You entered:\n" + url
                +"\n Status: \n" + "Received Successfully!"
                +"\n Short Code: \n" + shortCode;
    }
}