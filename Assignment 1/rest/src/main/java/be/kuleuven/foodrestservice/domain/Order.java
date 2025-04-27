package be.kuleuven.foodrestservice.domain;

import java.util.List;
import java.util.Objects;

public class Order {

    protected String id;
    protected String address;
    protected List<String> mealIds;

    public List<String> getMealIds() {
        return mealIds;
    }

    public void setMealIds(List<String> mealIds) {
        this.mealIds = mealIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(address, order.address) && Objects.equals(mealIds, order.mealIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, mealIds);
    }
}

