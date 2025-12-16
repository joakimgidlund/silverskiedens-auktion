package se.yrgo.gateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import se.yrgo.gateway.service.TestServiceImpl;

@RestController
public class TestController {

    @Autowired
    private TestServiceImpl service;

    @GetMapping("/test")
    public String test() {
        return service.test();
    }
}
