package com.example.netflix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/endpoints")
public class EndpointController {

    private final RequestMappingHandlerMapping handlerMapping;

    @Autowired
    public EndpointController(ApplicationContext applicationContext) {
        this.handlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllEndpoints() {
        List<String> endpoints = handlerMapping.getHandlerMethods().keySet().stream()
                .map(mapping -> mapping.toString())
                .sorted()
                .collect(Collectors.toList());
        return ResponseEntity.ok(endpoints);
    }
}