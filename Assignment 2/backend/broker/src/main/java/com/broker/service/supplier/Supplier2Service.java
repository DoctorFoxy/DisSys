package com.broker.service.supplier;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.broker.dto.Supplier2ItemDTO;

@Service
public class Supplier2Service {
    private static final String BASE_URL = "https://mealsrest-cte3exhefsh2fscx.swedencentral-01.azurewebsites.net";
    private final RestTemplate restTemplate = new RestTemplate();

//    Retrieve details of an item by its item_id.,
    public String getSupplierItemById(Integer id) {
        String url = BASE_URL + "/item/{id}";
        return restTemplate.getForObject(url, String.class, id);
    }

//    Retrieve details of an item by its item_id with the DTO standards,
    public Supplier2ItemDTO getSupplierItemByIdDTO(Integer id) {
        String url = BASE_URL + "/item/{id}";
        return restTemplate.getForObject(url, Supplier2ItemDTO.class, id);
    }

    public String getSupplierItems() {
        String url = BASE_URL + "/item/8587";
        return restTemplate.getForObject(url, String.class);
    }

//    public String postSupplier2Item(Integer item_id) {
//        string url = BASE_URL + "/buy/{item_id}";
//        return restTemplate.getForObject(url, String.class, item_id);
//    }

//    GET /item/<int:item_id>
//    Retrieve details of an item by its item_id.,

//    POST /buy/<int:item_id>
//    string url = BASE_URL + "/buy/{item_id}";
//    Deprecated. Purchase an item by item_id and optional amount.,

//    POST /prepare/<int:reservation_id>/<int:item_id>
//    string url = BASE_URL + "/prepare/{reservation_id}/{item_id}";
//    Reserve an item by reservation_id and item_id. Marks the reservation as "prepared".,

//    POST /prepare/<int:reservation_id>/<int:item_id>/<int:amount>
//    string url = BASE_URL + "/prepare/{reservation_id}/{item_id}/{amount}";
//    Reserve a specified amount of an item.,

//    POST /abort/<int:reservation_id>
//    string url = BASE_URL + "/abort/{reservation_id}"
//    Abort a reservation by reservation_id, rolling back stock.,

//    POST /commit/<int:reservation_id>
//    string url = BASE_URL + "/commit/{reservation_id}"
//    Commit a reservation by reservation_id, finalizing the transaction.,

//    GET /test/log_db
//    string url = BASE_URL + "/test/log_db"
//    Retrieve all records from the reservations table (debugging).,

//    POST /test/clear_reservations
//    Clear all entries from the reservations table (debugging).,

//    POST /test/reset_stock
//    Reset all items' stock to 100 (debugging).

}