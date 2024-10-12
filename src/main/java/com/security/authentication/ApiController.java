package com.security.authentication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.annotation.Secured;

@RestController
public class ApiController {
    @GetMapping("/public/hello")
    public String publicEndpoint() {
        return "Hello, World!";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/secure")
    public String adminEndpoint() {
        return "Admin only data";
    }
}