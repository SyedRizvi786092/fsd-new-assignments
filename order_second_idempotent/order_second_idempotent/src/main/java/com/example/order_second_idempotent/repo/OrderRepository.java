package com.example.order_second_idempotent.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.order_second_idempotent.entity.Order;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	Optional<Order> findByIdempotencyKey(String key);
}
