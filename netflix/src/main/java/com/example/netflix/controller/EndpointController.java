package com.example.netflix.controller;

import com.example.netflix.dto.EndpointListDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
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

    public EndpointController(@Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @Operation(
            summary = "Get all registered API endpoints",
            description = "Returns a list of all endpoints available in the application",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = {
                                    @Content(mediaType = "application/json"),
                                    @Content(mediaType = "application/xml")
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = {
                                    @Content(mediaType = "application/json"),
                                    @Content(mediaType = "application/xml")
                            }
                    )
            }
    )
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EndpointListDTO> getAllEndpoints() {
        List<String> endpoints = handlerMapping.getHandlerMethods().keySet().stream()
                .map(Object::toString)
                .sorted()
                .collect(Collectors.toList());
        if (endpoints.isEmpty()) {
            throw new RuntimeException("No endpoints registered.");
        }
        return ResponseEntity.ok(new EndpointListDTO(endpoints));
    }
}
