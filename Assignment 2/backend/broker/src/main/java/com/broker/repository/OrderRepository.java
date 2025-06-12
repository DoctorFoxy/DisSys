package com.broker.repository;

import com.broker.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUserId(String user_id);

    Optional<Order> findByItemId(Integer itemId);

    List<Order> findAllByStatus(String status);
}
