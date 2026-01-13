package com.example.order_second_idempotent.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Order {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Product name cannot be blank")
	@Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
	@Column(name = "prod_name", nullable = false, length = 100)
	private String prodName;
	
	@Column(name = "quantity", nullable = false)
	private int quantity;
	
	// Idempotency key (unique)
	@Column(name = "idempotency_key", nullable = false, unique = true, length = 128)
	private String idempotencyKey;


}
