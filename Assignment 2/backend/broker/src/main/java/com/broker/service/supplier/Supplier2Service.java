package com.broker.service.supplier;

import com.broker.dto.Supplier2PurchaseResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class Supplier2Service implements Supplier {
    private static final String BASE_URL = "https://mealsrest-cte3exhefsh2fscx.swedencentral-01.azurewebsites.net";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean prepareReservation(int orderId, String supplierItemId, int quantity) throws TimeoutException {
        String url = String.format("%s/prepare/%d/%s/%d", BASE_URL, orderId, supplierItemId, quantity);
        try {
            Supplier2PurchaseResponseDTO dto = restTemplate.postForObject(url, null, Supplier2PurchaseResponseDTO.class);
            return dto != null && dto.isSuccess();
        } catch (HttpClientErrorException e) {
            Supplier2PurchaseResponseDTO dto = parseErrorDTO(e);
            if (dto.isError()) throw new TimeoutException("Prepare failed: " + dto.getError());
            return dto.isSuccess(); // fallback if no error field, but message indicates success
        }
    }

    @Override
    public void commitReservation(int orderId) throws TimeoutException {
        String url = String.format("%s/commit/%d", BASE_URL, orderId);
        try {
            Supplier2PurchaseResponseDTO dto = restTemplate.postForObject(url, null, Supplier2PurchaseResponseDTO.class);
            if (dto != null && dto.isError()) throw new TimeoutException("Commit failed: " + dto.getError());
        } catch (HttpClientErrorException e) {
            Supplier2PurchaseResponseDTO dto = parseErrorDTO(e);
            throw new TimeoutException("Commit failed: " + dto.getError());
        }
    }

    @Override
    public void abortReservation(int orderId) throws TimeoutException {
        String url = String.format("%s/abort/%d", BASE_URL, orderId);
        try {
            Supplier2PurchaseResponseDTO dto = restTemplate.postForObject(url, null, Supplier2PurchaseResponseDTO.class);
            if (dto != null && dto.isError()) throw new TimeoutException("Abort failed: " + dto.getError());
        } catch (HttpClientErrorException e) {
            Supplier2PurchaseResponseDTO dto = parseErrorDTO(e);
            throw new TimeoutException("Abort failed: " + dto.getError());
        }
    }

    private Supplier2PurchaseResponseDTO parseErrorDTO(HttpClientErrorException e) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(e.getResponseBodyAsString(), Supplier2PurchaseResponseDTO.class);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to parse Supplier2 error response", ex);
        }
    }
}







//package com.broker.service.supplier;
//
//import com.broker.dto.Supplier2ItemDTO;
//import com.broker.dto.Supplier2PurchaseResponseDTO;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.io.IOException;
//
//@Service
//public class Supplier2Service {
//    private static final String BASE_URL = "https://mealsrest-cte3exhefsh2fscx.swedencentral-01.azurewebsites.net";
//    private final RestTemplate restTemplate = new RestTemplate();
//
////////////////////////////////////////////////////////////////////////////
////  Retrieve details of an item by its item_id.,
//    public String getSupplierItemById(Integer itemId) {
////        String url = BASE_URL + "/item/{id}";
//        String url = BASE_URL + "/item/" + itemId;
//        return restTemplate.getForObject(url, String.class, itemId);
//    }
//
////  Retrieve details of an item by its item_id with the DTO standards,
//    public Supplier2ItemDTO getSupplierItemByIdDTO(Integer itemId) {
////        String url = BASE_URL + "/item/{id}";
//        String url = BASE_URL + "/item/" + itemId;
//        return restTemplate.getForObject(url, Supplier2ItemDTO.class, itemId);
//    }
//
//
////  Buy Item
//    public String buyItem(Integer itemId) {
////        String url = BASE_URL + "/buy/{itemId}";
//        String url = BASE_URL + "/buy/" + itemId;
//        return restTemplate.postForObject(url, null, String.class, itemId);
//    }
//
//
//    public Supplier2PurchaseResponseDTO buyItemWithQuantity(String itemId, int amount) {
//        String url = BASE_URL + "/buy/" + itemId + "/" + amount;
//
//        try {
//            ResponseEntity<Supplier2PurchaseResponseDTO> response = restTemplate.postForEntity(
//                    url, null, Supplier2PurchaseResponseDTO.class);
//            return response.getBody();
//        } catch (HttpClientErrorException e) {
//            try {
//                ObjectMapper mapper = new ObjectMapper();
//                return mapper.readValue(e.getResponseBodyAsString(), Supplier2PurchaseResponseDTO.class);
//            } catch (IOException ioException) {
//                throw new RuntimeException("Error parsing Supplier2 error response", ioException);
//            }
//        }
//    }
//
////////////////////////////////////////////////////////////////////////////////////////////
////    public boolean prepareReservation(int reservationId, int itemId, int amount) {
////        String url = BASE_URL + "/prepare/" + reservationId + "/" + itemId + "/" + amount;
////        try {
////            ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
////            String body = response.getBody();
////            return body != null && (body.contains("prepared") || body.contains("already prepared"));
////        } catch (HttpClientErrorException e) {
////            String err = e.getResponseBodyAsString();
////            System.out.println("Prepare failed: " + err);
////            return err.contains("already prepared");
////        } catch (Exception e) {
////            System.out.println("Prepare error: " + e.getMessage());
////            return false;
////        }
////    }
////
////    public boolean commitReservation(int reservationId) {
////        String url = BASE_URL + "/commit/" + reservationId;
////        try {
////            ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
////            String body = response.getBody();
////            return body != null && (body.contains("committed") || body.contains("already committed"));
////        } catch (HttpClientErrorException e) {
////            String err = e.getResponseBodyAsString();
////            System.out.println("Commit failed: " + err);
////            return err.contains("already committed");
////        } catch (Exception e) {
////            System.out.println("Commit error: " + e.getMessage());
////            return false;
////        }
////    }
////
////    public boolean abortReservation(int reservationId) {
////        String url = BASE_URL + "/abort/" + reservationId;
////        try {
////            ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
////            String body = response.getBody();
////            return body != null && (body.contains("aborted") || body.contains("already aborted"));
////        } catch (HttpClientErrorException e) {
////            String err = e.getResponseBodyAsString();
////            System.out.println("Abort failed: " + err);
////            return err.contains("already aborted");
////        } catch (Exception e) {
////            System.out.println("Abort error: " + e.getMessage());
////            return false;
////        }
////    }
//
//    public Supplier2PurchaseResponseDTO prepareReservation(int reservationId, int itemId, int amount) {
//        String url = BASE_URL + "/prepare/" + reservationId + "/" + itemId + "/" + amount;
//        try {
//            ResponseEntity<Supplier2PurchaseResponseDTO> response =
//                    restTemplate.postForEntity(url, null, Supplier2PurchaseResponseDTO.class);
//            return response.getBody();
//        } catch (HttpClientErrorException e) {
//            return extractErrorDTO(e);
//        }
//    }
//
//    public Supplier2PurchaseResponseDTO commitReservation(int reservationId) {
//        String url = BASE_URL + "/commit/" + reservationId;
//        try {
//            ResponseEntity<Supplier2PurchaseResponseDTO> response =
//                    restTemplate.postForEntity(url, null, Supplier2PurchaseResponseDTO.class);
//            return response.getBody();
//        } catch (HttpClientErrorException e) {
//            return extractErrorDTO(e);
//        }
//    }
//
//    public Supplier2PurchaseResponseDTO abortReservation(int reservationId) {
//        String url = BASE_URL + "/abort/" + reservationId;
//        try {
//            ResponseEntity<Supplier2PurchaseResponseDTO> response =
//                    restTemplate.postForEntity(url, null, Supplier2PurchaseResponseDTO.class);
//            return response.getBody();
//        } catch (HttpClientErrorException e) {
//            return extractErrorDTO(e);
//        }
//    }
//
//    private Supplier2PurchaseResponseDTO extractErrorDTO(HttpClientErrorException e) {
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            return mapper.readValue(e.getResponseBodyAsString(), Supplier2PurchaseResponseDTO.class);
//        } catch (IOException ioException) {
//            throw new RuntimeException("Error parsing Supplier2 error response", ioException);
//        }
//    }
//}