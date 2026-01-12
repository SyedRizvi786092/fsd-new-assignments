package com.example.order_second_idempotent.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.order_second_idempotent.entity.Order;
import com.example.order_second_idempotent.repo.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;
	
	//implement Transactional in service layer
	@Transactional
	public Order create(Order o,String idempotencyKey) {
		Optional<Order> existing = orderRepository.findByIdempotencyKey(idempotencyKey);
		if(existing == null || idempotencyKey.isBlank()) {
			throw new IllegalArgumentException("Key is required");
		}
		Order o1=new Order();
		o1.setProdName(o.getProdName());
		o1.setQuantity(o.getQuantity());
		o1.setIdempotencyKey(idempotencyKey);
		return orderRepository.save(o);
	}
	
	@Transactional
	public Optional<Order> getOrder(Long id){
		Optional<Order> o=orderRepository.findById(id);
		if(o==null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return o;
	}
}
