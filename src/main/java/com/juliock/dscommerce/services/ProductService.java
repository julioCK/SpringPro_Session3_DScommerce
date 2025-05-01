package com.juliock.dscommerce.services;

import com.juliock.dscommerce.dto.ProductDTO;
import com.juliock.dscommerce.entities.Product;
import com.juliock.dscommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        Product product = productOptional.get();
        return new ProductDTO(product);
    }
}
