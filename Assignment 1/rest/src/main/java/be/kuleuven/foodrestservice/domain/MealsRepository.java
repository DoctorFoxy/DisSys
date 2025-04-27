package be.kuleuven.foodrestservice.domain;

import be.kuleuven.foodrestservice.exceptions.MealCreationException;
import be.kuleuven.foodrestservice.exceptions.MealNotFoundException;
import be.kuleuven.foodrestservice.exceptions.OrderCreationException;
import be.kuleuven.foodrestservice.exceptions.OrderNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class MealsRepository {
    // map: id -> meal
    private static final Map<String, Meal> meals = new HashMap<>();
    private static final Map<String, Order> orders = new HashMap<>();

    @PostConstruct
    public void initData() {

        Meal a = new Meal();
        a.setId("5268203c-de76-4921-a3e3-439db69c462a");
        a.setName("Steak");
        a.setDescription("Steak with fries");
        a.setMealType(MealType.MEAT);
        a.setKcal(1100);
        a.setPrice((10.00));

        meals.put(a.getId(), a);

        Meal b = new Meal();
        b.setId("4237681a-441f-47fc-a747-8e0169bacea1");
        b.setName("Portobello");
        b.setDescription("Portobello Mushroom Burger");
        b.setMealType(MealType.VEGAN);
        b.setKcal(637);
        b.setPrice((7.00));

        meals.put(b.getId(), b);

        Meal c = new Meal();
        c.setId("cfd1601f-29a0-485d-8d21-7607ec0340c8");
        c.setName("Fish and Chips");
        c.setDescription("Fried fish with chips");
        c.setMealType(MealType.FISH);
        c.setKcal(950);
        c.setPrice(5.00);

        meals.put(c.getId(), c);
    }

    public Optional<Meal> findMeal(String id) {
        Assert.notNull(id, "The meal id must not be null");
        Meal meal = meals.get(id);
        return Optional.ofNullable(meal);
    }

    public Collection<Meal> getAllMeal() {
        return meals.values();
    }

    public Meal getCheapest() {
        return meals.values().stream()
                .min(Comparator.comparingDouble(Meal::getPrice))
                .orElseThrow(() -> new MealNotFoundException("cheapest meal"));
    }

    public Meal getLargestMeal() {
        return meals.values().stream()
                .max(Comparator.comparingInt(Meal::getKcal))
                .orElseThrow(() -> new MealNotFoundException("most expensive meal"));
    }

    public Meal addMeal(Meal newMeal) {
        if (newMeal.description == null
                || newMeal.description.isEmpty()
                || newMeal.kcal <= 0
                || newMeal.name == null
                || newMeal.name.isEmpty()
                || newMeal.price <= 0) {
            throw new MealCreationException();
        }

        newMeal.setId(generateId());
        meals.put(newMeal.getId(), newMeal);
        return newMeal;
    }

    public Meal updateMeal(Meal meal) {
        String id = meal.getId();

        Meal existingMeal = meals.get(id);
        if (existingMeal == null) {
            throw new MealNotFoundException(id);
        }

        meals.put(id, meal);
        return meal;
    }

    public Meal deleteMeal(String id) {
        Meal deleted = meals.remove(id);
        if(deleted == null) {
            throw new MealNotFoundException(id);
        }
        return deleted;
    }

    public Collection<Order> getAllOrders() {
        return orders.values();
    }

    public Order addOrder(Order newOrder) {
        if (newOrder.address == null
                || newOrder.address.isEmpty()
                || newOrder.mealIds == null
                || newOrder.mealIds.isEmpty()) {
            throw new OrderCreationException();
        }

        newOrder.setId(generateId());
        orders.put(newOrder.getId(), newOrder);
        return newOrder;
    }

    public Order deleteOrder(String id) {
        Order deleted = orders.remove(id);
        if(deleted == null) {
            throw new OrderNotFoundException(id);
        }
        return deleted;
    }

    private String generateId() {
        // Missing check if generated UUID is already in use, but whatever
        return UUID.randomUUID().toString();
    }
}
