package com.broker.service.supplier;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.broker.dto.Supplier2ItemDTO;

@Service
public class Supplier2Service {
    private final RestTemplate restTemplate = new RestTemplate();

    public String getSupplierItemById(Integer id) {
        String url = "https://mealsrest-cte3exhefsh2fscx.swedencentral-01.azurewebsites.net/item/{id}";
        return restTemplate.getForObject(url, String.class, id);
    }

    public Supplier2ItemDTO getSupplierItemByIdDTO(Integer id) {
        String url = "https://mealsrest-cte3exhefsh2fscx.swedencentral-01.azurewebsites.net/item/{id}";
        return restTemplate.getForObject(url, Supplier2ItemDTO.class, id);
    }
}