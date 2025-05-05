package com.juliock.dscommerce.controllers;

import com.juliock.dscommerce.dto.ProductDTO;
import com.juliock.dscommerce.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {

        /*
        *   Caso o ID do produto não seja encontrado pelo metodo productService.findProductById(), a EXCEPTION 'ResourceNotFoundException' vai ser lançada.
        *
        *   Pra evitar boilerplate do try/catch, é possivel criar uma classe para tratar exceptions na camada Controller: 'ControllerExceptionHandler'.
        *       Para organizar melhor, o ideal é criar um pacote 'handlers' para conter essa classe.
        * */

        ProductDTO dto = productService.findProductById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "")
    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable) {

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

        Page<ProductDTO> page = productService.findAllProducts(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO productDTO) {
    /*
    *   @RequestBody sinaliza ao Spring Boot para converter o conteúdo do body (nesse caso o JSON contendo um novo registro a ser inserido com POST) para o tipo ProductDTO.
    *       Para que isso aconteça sem erros, os nomes dos campos do JSON devem corresponder aos nomes dos atributos da classe DTO.
    * */

    /*
    *  Para se ter mais controle sobre a resposta Http enviada ao cliente, usamos a classe ResponseEntity que permite, entre outras coisas, controlar o codigo de status da resposta.
    *       No caso de uma inclusão bem sucedida de um novo registro, o código de status adequado é o 201.
    * */

    /*
    *   Seguindo as boas-praticas de uma API REST, ao inserir um novo registro no BD com metodo POST, é necessário retornar a URI que aponta diretamente para esse novo recurso.
    *       Ou seja, após a requisição, a resposta deve conter o caminho para o registro que foi inserido.
    *
    *   URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
    *       O codigo acima cria dinamicamente a URI que será retornada após uma inserção bem sucedida.
    *
    *           - ServletUriComponentsBuilder.fromCurrentRequest() vai pegar a URL da REQUISIÇÃO atual para construir a base da URI que será retornada (ex http://localhost:8080/usuarios)
    *           - path("/{id}") estabelece um placeholder onde será adicionado dinamicamente o id do novo registro adicionado
    *           - buildAndExpand(dto.getId()) substitui o placeholder pelo id do objeto recém-criado, (ex de resultado http://localhost:8080/usuarios/26)
    *           - toUri() converte o resultado final em um objeto URI
    *
    *   Ao usar "ResponseEntity.created(uri).body(dto);" estamos respondendo incluindo um cabeçalho 'Location' com a URI do objeto inserido e com o cod de status 201.
    *
    *   A anotation @Valid é da Jakarta Validation API e aciona o gatilho para que uma validação dos dados anotados do objeto DTO sejam validade
    *       de acordo com constraints parametrizadas em cada campo da classe ProductDTO.
    * */
        ProductDTO dto = productService.insertProduct(productDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
        dto = productService.updateProduct(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
    /*
    *   O metodo deleteProductById vai deletar o registro porém nao retornará nada. Dessa forma, no metodo abaixo o ResponseEntity nao vai ter corpo.
    *       ResponseEntity.noContent(): quando uma resposta foi bem sucedida porém nao tem corpo, o código é 204(No Content). O metodo noContent() providencia essa personalização do cod.
    *       O metodo build() finalizado a construção do ResponseEntity;
    * */
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }
}
