package com.juliock.dscommerce.services;

import com.juliock.dscommerce.dto.ProductDTO;
import com.juliock.dscommerce.entities.Product;
import com.juliock.dscommerce.repositories.ProductRepository;
import com.juliock.dscommerce.services.exceptions.DbIntegrityException;
import com.juliock.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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

        /*  se o findById() nao encontrar nenhum objeto para atribuir ao Optional, o metodo orElseThrow() vai verificar que o Optional está vazio e lançar a exception
                fornecida através da interface funcional Supplier<T>
            Supplier<T> é uma interface funcional, isso significa que ela possui um unico metodo abstrato

                public interface Supplier<T> {
                    T get();
                }
            Quando uma interface é funcional, é possível usar uma expressão lambida que chama seu unico metodo usando () -> argumento para o metodo abstrato
         */

        Product product = productOptional.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found"));
        return new ProductDTO(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllProducts(Pageable pageable) {
        Page<Product> result = productRepository.findAll(pageable);
        Page<ProductDTO> productDTOPageList = result.map(x -> new ProductDTO(x));
        return productDTOPageList;
    }

    @Transactional
    public ProductDTO insertProduct(ProductDTO pDTO) {
    /*  Inserir um novo produto. O retorno do tipo ProductDTO vai ser util pois depois da persistencia com productRepository devemos retornar um DTO com o ID que foi gerado pelo DB
    *   O metodo save() do JpaRepository retorna a entidade salva no DB. Se o valor do campo iD ja existir no banco, o registro será atualizado, se o iD for null, será criado um novo registro.
    * */
        Product product = new Product();
        setDTOtoProduct(pDTO, product);
        product = productRepository.save(product); // O metodo save() do JpaRepository retorna a entidade salva no DB. Se o valor do campo iD ja existir no banco, o registro será atualizado, se o iD for null, será criado um novo registro.
        return new ProductDTO(product);
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO dto) {
        try {
            Product product = productRepository.getReferenceById(id);
            setDTOtoProduct(dto, product);
            product = productRepository.save(product);
            return new ProductDTO(product);
        } catch(EntityNotFoundException e) {
            throw new ResourceNotFoundException("Resource Not Found");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS) //   revise.
    public void deleteProductById(Long id) {

        /*  Mesmo que o id fornecido nao exista, aparentemente o metodo deleteByID da interface do JpaRepository nao lança nenhuma exception,
                portanto temos que testar se o id existe antes de executar o metodo.    */

        if(!productRepository.existsById(id))
            throw new ResourceNotFoundException("Resource Not Found");  //  Se o ID nao existir lança a exception

        /*  Agora, mesmo que exista o registro com o ID fornecido, se esse registro fizer referencia a outro registro, haverá uma
        *       FALHA DE INTEGRIDADE REFERENCIAL ao tentar deletá-lo.
        *       O metodo vai lançar uma DataIntegrityViolationException */

        try {
            productRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DbIntegrityException("Database Integrity Constraint Violation");
        }
    }

    private Product setDTOtoProduct(ProductDTO dto, Product product) {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImgUrl(dto.getImgUrl());

        return product;
    } //cria objetos DTO a partir de objetos Produto.
}
