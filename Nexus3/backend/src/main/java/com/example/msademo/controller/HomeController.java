package com.example.msademo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;

@RestController
public class HomeController {
    @GetMapping("/")
    public @ResponseBody String get() {
        String resp;
        try {
            resp = "Hello World! from: " +
                    InetAddress.getLocalHost().getHostName();
        } catch (Exception ex) {
            resp = "Error :: " + ex.getMessage();
        }
        return resp;
    }
}
