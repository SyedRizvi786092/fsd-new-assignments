package com.example.order_second_idempotent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.order_second_idempotent.entity.Order;
import com.example.order_second_idempotent.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@PostMapping
	public ResponseEntity<Order> create(@Valid @RequestBody Order order,String idempotencyKey){
		return new ResponseEntity<Order>(orderService.create(order, idempotencyKey),HttpStatus.CREATED);
	}
	
	@GetMapping("/id")
	public ResponseEntity<Order> getByOrder(@PathVariable Long id){
		return orderService.getOrder(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

}
