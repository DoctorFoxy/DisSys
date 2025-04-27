package be.kuleuven.foodrestservice.controllers;

import be.kuleuven.foodrestservice.domain.Meal;
import be.kuleuven.foodrestservice.domain.MealsRepository;
import be.kuleuven.foodrestservice.domain.Order;
import be.kuleuven.foodrestservice.exceptions.MealNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
public class MealsRestRpcStyleController {

    private final MealsRepository mealsRepository;

    @Autowired
    MealsRestRpcStyleController(MealsRepository mealsRepository) {
        this.mealsRepository = mealsRepository;
    }

    @GetMapping("/restrpc/meals/{id}")
    Meal getMealById(@PathVariable String id) {
        Optional<Meal> meal = mealsRepository.findMeal(id);

        return meal.orElseThrow(() -> new MealNotFoundException(id));
    }

    @GetMapping("/restrpc/meals")
    Collection<Meal> getMeals() {
        return mealsRepository.getAllMeal();
    }

    @GetMapping("/restrpc/meals/cheapest")
    Meal getCheapestMeal() {
        return mealsRepository.getCheapest();
    }

    @GetMapping("/restrpc/meals/largest")
    Meal getLargestMeal() {
        return mealsRepository.getLargestMeal();
    }

    @PostMapping("/restrpc/meals")
    Meal addNewMeal(@RequestBody Meal newMeal) {
        return mealsRepository.addMeal(newMeal);
    }

    @PutMapping("/restrpc/meals/{id}")
    Meal updateMeal(@PathVariable String id, @RequestBody Meal newMeal) {
        if (!id.equals(newMeal.getId())) {
            throw new Error("ID passed in url and in body don't match!");
        }

        return mealsRepository.updateMeal(newMeal);
    }

    @DeleteMapping("/restrpc/meals/{id}")
    void deleteMeal(@PathVariable String id) {
        mealsRepository.deleteMeal(id);
    }

    @GetMapping("/restrpc/orders")
    Collection<Order> getOrders() {
        return mealsRepository.getAllOrders();
    }

    @PostMapping("/restrpc/orders")
    Order addNewOrder(@RequestBody Order newOrder) {
        return mealsRepository.addOrder(newOrder);
    }

    @DeleteMapping("/restrpc/orders/{id}")
    void deleteOrder(@PathVariable String id) {
        mealsRepository.deleteOrder(id);
    }
}
