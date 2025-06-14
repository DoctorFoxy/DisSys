package com.broker.service.supplier;

import com.broker.dto.Supplier1PurchaseResponseDTO;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.util.UUID;

@Service
public class Supplier1Service implements Supplier {
    private static final String BASE_URL = "http://shubhamvmeurope.westeurope.cloudapp.azure.com:8080";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean prepareReservation(UUID orderId, String supplierItemId, int quantity) throws TimeoutException {
        String url = String.format("%s/api/medicins/prepare/%s/%s/%d", BASE_URL, orderId.toString(), supplierItemId, quantity);

        try {
            String response = restTemplate.postForObject(url, null, String.class);
            Supplier1PurchaseResponseDTO dto = new Supplier1PurchaseResponseDTO(response);
            return dto.isSuccess();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(409))) {
                return false;   // Fuck you djeniz
            }
            throw new TimeoutException("Error during prepareReservation: " + e.getMessage());
        }
    }

    @Override
    public void commitReservation(UUID orderId) throws TimeoutException {
        String url = String.format("%s/api/medicins/finalize/%s", BASE_URL, orderId.toString());

        try {
            String response = restTemplate.postForObject(url, null, String.class);
            Supplier1PurchaseResponseDTO dto = new Supplier1PurchaseResponseDTO(response);

            if (dto.isError()) {
                throw new TimeoutException("Commit failed: " + dto.getRawResponse());
            }
        } catch (RestClientException e) {
            throw new TimeoutException("Error during commitReservation: " + e.getMessage());
        }
    }

    @Override
    public void abortReservation(UUID orderId) throws TimeoutException {
        String url = String.format("%s/api/medicins/abort/%s", BASE_URL, orderId.toString());

        try {
            String response = restTemplate.postForObject(url, null, String.class);
            Supplier1PurchaseResponseDTO dto = new Supplier1PurchaseResponseDTO(response);

            if (dto.isError()) {
                throw new TimeoutException("Abort failed: " + dto.getRawResponse());
            }
        } catch (RestClientException e) {
            throw new TimeoutException("Error during abortReservation: " + e.getMessage());
        }
    }
}




//@Service
//public class Supplier1Service {
//    private static final String BASE_URL = "http://shubhamvmeurope.westeurope.cloudapp.azure.com:8080";
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    public String getSupplierItemById(Integer id) {
//        String url = BASE_URL + "/api/medicins/{id}";
//        return restTemplate.getForObject(url, String.class, id);
//    }
//
//    public Supplier1PurchaseResponseDTO prepareReservation(int reservationId, int medicinId, int quantity) {
//        String url = BASE_URL + "/api/medicins/prepare/" + reservationId + "/" + medicinId + "/" + quantity;
//        return postForResponse(url);
//    }
//
//    public Supplier1PurchaseResponseDTO prepareReservationWithEndPoint(int reservationId, int medicinId, String endpoint, int quantity) {
//        String url = BASE_URL + "/api/medicins/prepare/" + reservationId + "/" + medicinId + "/" + endpoint + "/" + quantity;
//        return postForResponse(url);
//    }
//
//    public Supplier1PurchaseResponseDTO commitReservation(int reservationId) {
//        String url = BASE_URL + "/api/medicins/finalize/" + reservationId;
//        return postForResponse(url);
//    }
//
//    public Supplier1PurchaseResponseDTO abortReservation(int reservationId) {
//        String url = BASE_URL + "/api/medicins/abort/" + reservationId;
//        return postForResponse(url);
//    }
//
//    public Supplier1PurchaseResponseDTO checkReservationStatus(int reservationId) {
//        String url = BASE_URL + "/api/medicins/status/" + reservationId;
//        try {
//            return restTemplate.getForObject(url, Supplier1PurchaseResponseDTO.class);
//        } catch (HttpClientErrorException e) {
//            return extractErrorDTO(e);
//        }
//    }
//
//    private Supplier1PurchaseResponseDTO postForResponse(String url) {
//        try {
//            ResponseEntity<Supplier1PurchaseResponseDTO> response =
//                    restTemplate.postForEntity(url, null, Supplier1PurchaseResponseDTO.class);
//
//            Supplier1PurchaseResponseDTO body = response.getBody();
//            System.out.println("Supplier1 response: " + (body != null ? body.getMessage() : "null"));
//            return body;
//        } catch (HttpClientErrorException e) {
//            return extractErrorDTO(e);
//        }
//    }
//
//    private Supplier1PurchaseResponseDTO extractErrorDTO(HttpClientErrorException e) {
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            return mapper.readValue(e.getResponseBodyAsString(), Supplier1PurchaseResponseDTO.class);
//        } catch (IOException ioException) {
//            throw new RuntimeException("Failed to parse Supplier1 error response", ioException);
//        }
//    }
//
//    public boolean isSuccessful(Supplier1PurchaseResponseDTO response) {
//        return response != null && response.isSuccess();
//    }
//}