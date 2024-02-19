package com.pamela.desafioanotaai.controllers;


import com.pamela.desafioanotaai.domain.category.Category;
import com.pamela.desafioanotaai.domain.category.CategoryDTO;
import com.pamela.desafioanotaai.domain.product.Product;
import com.pamela.desafioanotaai.domain.product.ProductDTO;
import com.pamela.desafioanotaai.service.CategoryService;
import com.pamela.desafioanotaai.service.ProductService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Product> insert(@RequestBody ProductDTO productDTO) {
        Product newProduct = this.service.insert(productDTO);
        return ResponseEntity.ok().body(newProduct);


    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = this.service.getAll();
        return ResponseEntity.ok().body(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable("id") String id, @RequestBody ProductDTO productDTO) {
        Product updateProduct = this.service.update(id, productDTO);
        return ResponseEntity.ok().body(updateProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathParam("id") String id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();

    }
}
