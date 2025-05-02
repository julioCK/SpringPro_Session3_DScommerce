package com.juliock.dscommerce.controllers;

import com.juliock.dscommerce.dto.ProductDTO;
import com.juliock.dscommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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
        return productService.findProductById(id);
    }

    /*
    *   O Spring possui a interface "Pageable" que organiza o resultado das consultas ao DB em páginas.
    *   Para que isso aconteça é necessário fornecer um Objeto Pageable como parametro para o metodo que vai realizar a operação no banco de dados.
    *       (findAll do Controller -> findAllProducts do Service -> findAll do Repository é o metodo que vai buscar os dados)
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
        return productService.findAllProducts(pageable);
    }

    /*
    *   @RequestBody sinaliza ao Spring Boot para converter o conteúdo do body (nesse caso o JSON contendo um novo registro a ser inserido com POST) para o tipo ProductDTO.
    *       Para que isso aconteça sem erros, os nomes dos campos do JSON devem corresponder aos nomes dos atributos da classe DTO.
    * */

    @PostMapping
    public ProductDTO insert(@RequestBody ProductDTO productDTO) {
        return productService.insertNewProduct(productDTO);
    }
}
