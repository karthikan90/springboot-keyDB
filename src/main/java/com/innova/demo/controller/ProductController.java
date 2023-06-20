package com.innova.demo.controller;

import com.innova.demo.entity.Product;
import com.innova.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping
	public ResponseEntity<List<Product>> get() {
		return ResponseEntity.ok(productService.getProducts());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
		return ResponseEntity.ok(productService.getProductById(id));
	}

	@PostMapping
	public ResponseEntity<Product> create(@RequestBody Product product) {
		return ResponseEntity.ok(productService.create(product));
	}

	@PutMapping("/{id})")
	public ResponseEntity<Product> update(@PathVariable int id, @RequestBody Product product) {
		return ResponseEntity.ok(productService.update(id, product));
	}

	@DeleteMapping("/{id}")
	@CacheEvict(value = "products", key = "#id")
	public void delete(@PathVariable int id) {
		productService.deleteById(id);
	}

}
