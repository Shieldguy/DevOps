package com.example.msademo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class HomeControllerTest {
    @Test
    public void testGet() {
        HomeController controller = new HomeController();
        String out = controller.get();
        assertTrue(out.startsWith("Hello World! from: "));
    }
}
