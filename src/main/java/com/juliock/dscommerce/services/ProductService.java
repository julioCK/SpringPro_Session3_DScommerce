package com.juliock.dscommerce.services;

import com.juliock.dscommerce.dto.ProductDTO;
import com.juliock.dscommerce.entities.Product;
import com.juliock.dscommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public ProductDTO findProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        Product product = productOptional.get();
        return new ProductDTO(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllProducts(Pageable pageable) {
        Page<Product> result = productRepository.findAll(pageable);
        Page<ProductDTO> productDTOPageList = result.map(x -> new ProductDTO(x));
        return productDTOPageList;
    }

    /*  Inserir um novo produto. O retorno do tipo ProductDTO vai ser util pois depois da persistencia com productRepository devemos retornar um DTO com o ID que foi gerado pelo DB
    *   O metodo save() do JpaRepository retorna a entidade salva no DB. Se o valor do campo iD ja existir no banco, o registro será atualizado, se o iD for null, será criado um novo registro.
    * */
    @Transactional
    public ProductDTO insertNewProduct(ProductDTO pDTO) {
        Product p = productRepository.save(new Product(null, pDTO.getName(), pDTO.getDescription(), pDTO.getPrice(), pDTO.getImgUrl()));
        return new ProductDTO(p);
    }
}
