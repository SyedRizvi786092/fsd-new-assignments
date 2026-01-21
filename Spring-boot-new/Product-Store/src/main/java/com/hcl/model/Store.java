package com.hcl.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Store {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long Id;
	private String store_Name;
//	@ManyToOne
//	private Product product;

	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;
	
	public Store() {
		
	}

	public Store( String store_Name, Product product) {

		this.store_Name = store_Name;
		this.product = product;
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getStore_Name() {
		return store_Name;
	}

	public void setStore_Name(String store_Name) {
		this.store_Name = store_Name;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
