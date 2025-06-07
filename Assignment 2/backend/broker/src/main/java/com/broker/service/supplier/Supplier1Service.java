package com.broker.service.supplier;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Supplier1Service {
    private static final String BASE_URL = "http://shubhamvmeurope.westeurope.cloudapp.azure.com:8080";
    private final RestTemplate restTemplate = new RestTemplate();

    public String getSupplierItemById(Integer id) {
        String url = "http://shubhamvmeurope.westeurope.cloudapp.azure.com:8080/api/medicins/{id}";
        return restTemplate.getForObject(url, String.class, id);
    }
}

