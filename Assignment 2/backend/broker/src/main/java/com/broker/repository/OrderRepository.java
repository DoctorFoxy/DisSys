package com.broker.repository;

import com.broker.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUserId(String user_id);

    Optional<Order> findByItemId(Integer itemId);

    List<Order> findAllByStatus(String status);

    @Query("SELECT o.status FROM Order o WHERE o.id = :id") // More secure for only getting the status and no other info of the order
    Optional<String> findStatusById(@Param("id") Integer id);
}
