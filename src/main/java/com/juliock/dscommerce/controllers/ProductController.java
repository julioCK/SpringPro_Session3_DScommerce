package com.juliock.dscommerce.controllers;

import com.juliock.dscommerce.dto.ProductDTO;
import com.juliock.dscommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /* O @PathVariable diz que o parametro desse metodo será o valor recebido da rota mapeada, value = "/{id}"
    * */
    @GetMapping(value = "/{id}")
    public ProductDTO findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    /*
    *   O Spring possui a interface "Pageable" que organiza o resultado das consultas ao DB em páginas.
    *   Para que isso aconteça é necessário fornecer um Objeto Pageable como parametro para o metodo que vai realizar a operação no banco de dados.
    *       (findAll do Controller -> findAll do Service -> findAll do Repository é o metodo que vai buscar os dados)
    *       O retorno dessa operação sera uma coleção do tipo Page<T> que comporta o resultado da busca organizado em paginas.
    *
    *   O Spring lê automaticamente os parâmetros presentes na URL (page, size, sort, etc) se o Pageable for usado no metodo.
    *
    *   O Spring instancia e injeta automaticamente o objeto do tipo Pageable nesses metodos, pois no arquivo de configuração do Maven (pom.xml)
    *       foi adicionada a dependencia:
    *
    *       <groupId>org.springframework.boot</groupId>
	*		<artifactId>spring-boot-starter-data-jpa</artifactId>
    * */

    @GetMapping(value = "")
    public Page<ProductDTO> findAll(Pageable pageable) {
        return productService.findAll(pageable);
    }
}
