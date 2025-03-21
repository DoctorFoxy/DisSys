package be.kuleuven.foodrestservice.controllers;

import be.kuleuven.foodrestservice.domain.Meal;
import be.kuleuven.foodrestservice.domain.MealsRepository;
import be.kuleuven.foodrestservice.domain.Order;
import be.kuleuven.foodrestservice.exceptions.MealNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class MealsRestController {

    private final MealsRepository mealsRepository;

    @Autowired
    MealsRestController(MealsRepository mealsRepository) {
        this.mealsRepository = mealsRepository;
    }

    @GetMapping("/rest/meals/{id}")
    EntityModel<Meal> getMealById(@PathVariable String id) {
        Meal meal = mealsRepository.findMeal(id).orElseThrow(() -> new MealNotFoundException(id));

        return mealToEntityModel(id, meal);
    }

    @GetMapping("/rest/meals")
    CollectionModel<EntityModel<Meal>> getMeals() {
        Collection<Meal> meals = mealsRepository.getAllMeal();

        List<EntityModel<Meal>> mealEntityModels = new ArrayList<>();
        for (Meal m : meals) {
            EntityModel<Meal> em = mealToEntityModel(m.getId(), m);
            mealEntityModels.add(em);
        }
        return CollectionModel.of(mealEntityModels,
                linkTo(methodOn(MealsRestController.class).getMeals()).withSelfRel());
    }

    @GetMapping("/rest/meals/cheapest")
    EntityModel<Meal> getCheapestMeal() {
        Meal meal = mealsRepository.getCheapest();
        return mealToEntityModel(meal.getId(), meal);
    }

    @GetMapping("/rest/meals/largest")
    EntityModel<Meal> getLargestMeal() {
        Meal meal = mealsRepository.getLargestMeal();
        return mealToEntityModel(meal.getId(), meal);
    }

    @PostMapping("/rest/meals")
    EntityModel<Meal> addNewMeal(@RequestBody Meal newMeal) {
        Meal insertedMeal = mealsRepository.addMeal(newMeal);
        return mealToEntityModel(insertedMeal.getId(), insertedMeal);
    }

    @PutMapping("/rest/meals/{id}")
    EntityModel<Meal> updateMeal(@PathVariable String id, @RequestBody Meal newMeal) {
        if (!id.equals(newMeal.getId())) {
            throw new Error("ID passed in url and in body don't match!");
        }

        newMeal = mealsRepository.updateMeal(newMeal);
        return mealToEntityModel(newMeal.getId(), newMeal);
    }

    @DeleteMapping("/rest/meals/{id}")
    void deleteMeal(@PathVariable String id) {
        mealsRepository.deleteMeal(id);
    }

    private EntityModel<Meal> mealToEntityModel(String id, Meal meal) {
        return EntityModel.of(meal,
                linkTo(methodOn(MealsRestController.class).getMealById(id)).withSelfRel(),
                linkTo(methodOn(MealsRestController.class).getMeals()).withRel("rest/meals"));
    }

    @GetMapping("/rest/orders")
    CollectionModel<EntityModel<Order>> getOrders() {
        Collection<Order> orders = mealsRepository.getAllOrders();

        List<EntityModel<Order>> orderEntityModels = new ArrayList<>();
        for (Order o : orders) {
            EntityModel<Order> em = orderToEntityModel(o.getId(), o);
            orderEntityModels.add(em);
        }

        return CollectionModel.of(orderEntityModels,
                linkTo(methodOn(MealsRestController.class).getOrders()).withSelfRel());
    }

    @PostMapping("/rest/orders")
    EntityModel<Order> addNewOrder(@RequestBody Order newOrder) {
        Order insertedOrder =  mealsRepository.addOrder(newOrder);
        return orderToEntityModel(insertedOrder.getId(), insertedOrder);
    }

    @DeleteMapping("/rest/orders/{id}")
    void deleteOrder(@PathVariable String id) {
        mealsRepository.deleteOrder(id);
    }

    private EntityModel<Order> orderToEntityModel(String id, Order order) {
        return EntityModel.of(order,
                linkTo(methodOn(MealsRestController.class).getOrders()).withRel("rest/orders"));
    }

}
