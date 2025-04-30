package com.juliock.dscommerce.controllers;

import com.juliock.dscommerce.entities.Product;
import com.juliock.dscommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String teste() {
        Optional<Product> p = productRepository.findById(2L);
        Product product = p.get();
        return product.getName();
    }
}
