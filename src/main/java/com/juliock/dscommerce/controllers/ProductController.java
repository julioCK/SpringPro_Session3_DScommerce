package com.juliock.dscommerce.controllers;

import com.juliock.dscommerce.dto.ProductDTO;
import com.juliock.dscommerce.entities.Product;
import com.juliock.dscommerce.repositories.ProductRepository;
import com.juliock.dscommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /* O @PathVariable diz que o parametro desse metodo será o valor recebido da rota mapeada, value = "/{id}"
    * */
    @GetMapping(value = "/{id}")
    public ProductDTO findById(@PathVariable Long id) {
        return productService.findById(id);
    }
}
