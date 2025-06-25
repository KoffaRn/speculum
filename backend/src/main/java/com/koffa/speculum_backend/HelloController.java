package com.koffa.speculum_backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {
    @GetMapping("/public/hello")
    public String publicHello() {
        return "Hello";
    }
    @GetMapping("/private/hello")
    public String privateHello() {
        return "Hello in private";
    }
}
