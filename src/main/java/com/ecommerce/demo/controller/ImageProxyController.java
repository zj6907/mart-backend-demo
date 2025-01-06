package com.ecommerce.demo.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Spring Boot Reverse Proxy
 * This controller acts as a reverse proxy to fetch images from external sources
 * and serve them with a Cache-Control header instructing the browser to cache the image for 30 days.
 */
@Controller
public class ImageProxyController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/image/proxy")
    @ResponseBody
    @Cacheable("externalImages")
    public ResponseEntity<byte[]> proxyImage(@RequestParam("url") String imageUrl) {
        byte[] imageBytes = new byte[0];
        try {
            imageBytes = restTemplate.getForObject(imageUrl, byte[].class);
        } catch (Exception e) {
            System.out.println("[[[ failed for image ]]] " + imageUrl);
        }

        return ResponseEntity.ok()
                .header("Cache-Control", "public, max-age=2592000")  // Cache for 2592000=30days or 86400=1 day
                .body(imageBytes);
    }

}
