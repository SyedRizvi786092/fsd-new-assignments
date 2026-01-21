package com.hcl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.model.Product;
import com.hcl.repository.ProductRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class Controller {
	@Autowired
	private ProductRepository prepo;

	@PostMapping("/product")
	public void postMethodName() {
		Product p1 = new Product(null, "123 nagar");
//		return p;
	}

}
