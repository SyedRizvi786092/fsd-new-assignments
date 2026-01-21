package com.hcl.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Stock {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer stock_id;

	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinColumn(name = "product_id")
	private List<Product> product;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Store_id")
	private Store store;

}
