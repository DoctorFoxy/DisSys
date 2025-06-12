package com.broker.service.supplier;

import com.broker.dto.Supplier1PurchaseResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class Supplier1Service {
    private static final String BASE_URL = "http://shubhamvmeurope.westeurope.cloudapp.azure.com:8080";
    private final RestTemplate restTemplate = new RestTemplate();

    public String getSupplierItemById(Integer id) {
        String url = BASE_URL + "/api/medicins/{id}";
        return restTemplate.getForObject(url, String.class, id);
    }

    public Supplier1PurchaseResponseDTO prepareReservation(int reservationId, int medicinId, int quantity) {
        String url = BASE_URL + "/api/medicins/prepare/" + reservationId + "/" + medicinId + "/" + quantity;
        return postForResponse(url);
    }

    public Supplier1PurchaseResponseDTO commitReservation(int reservationId) {
        String url = BASE_URL + "/api/medicins/finalize/" + reservationId;
        return postForResponse(url);
    }

    public Supplier1PurchaseResponseDTO abortReservation(int reservationId) {
        String url = BASE_URL + "/api/medicins/abort/" + reservationId;
        return postForResponse(url);
    }

    public Supplier1PurchaseResponseDTO checkReservationStatus(int reservationId) {
        String url = BASE_URL + "/api/medicins/status/" + reservationId;
        try {
            return restTemplate.getForObject(url, Supplier1PurchaseResponseDTO.class);
        } catch (HttpClientErrorException e) {
            return extractErrorDTO(e);
        }
    }

    private Supplier1PurchaseResponseDTO postForResponse(String url) {
        try {
            ResponseEntity<Supplier1PurchaseResponseDTO> response =
                    restTemplate.postForEntity(url, null, Supplier1PurchaseResponseDTO.class);

            Supplier1PurchaseResponseDTO body = response.getBody();
            System.out.println("Supplier1 response: " + (body != null ? body.getMessage() : "null"));
            return body;
        } catch (HttpClientErrorException e) {
            return extractErrorDTO(e);
        }
    }

    private Supplier1PurchaseResponseDTO extractErrorDTO(HttpClientErrorException e) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(e.getResponseBodyAsString(), Supplier1PurchaseResponseDTO.class);
        } catch (IOException ioException) {
            throw new RuntimeException("Failed to parse Supplier1 error response", ioException);
        }
    }

    public boolean isSuccessful(Supplier1PurchaseResponseDTO response) {
        return response != null && response.isSuccess();
    }
}