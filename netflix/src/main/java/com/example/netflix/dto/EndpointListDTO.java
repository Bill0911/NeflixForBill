package com.example.netflix.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "endpoints")
public class EndpointListDTO {
    private List<String> endpoints;

    public EndpointListDTO() {}

    public EndpointListDTO(List<String> endpoints) {
        this.endpoints = endpoints;
    }

    public List<String> getEndpoints() { return endpoints; }
    public void setEndpoints(List<String> endpoints) { this.endpoints = endpoints; }
}
