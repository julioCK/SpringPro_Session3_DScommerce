package com.juliock.dscommerce.dto;


import com.juliock.dscommerce.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/*
        *   Não é adequado atender às requisições fornecendo objetos contendo TODOS os dados das entidades do banco.
        *   O ideal é extrair uma PROJEÇÃO dos dados da entidade, de acordo com a necessidade da requisição.
        *       Para realizar essa projeção dos dados de objetos recuperados do banco de dados é usada um objeto DTO (Data Transfer Object);
        *
        *   As validações dos dados serão feitas nos DTOs. É neles que usaremos as annotations do jakarta.validation-api, como @NotNull, @Size, @Email, etc.
        *       O motivo disso é por que os DTOs que fazem o tráfego dos dados.
        *       O gatilho para acionar a validação dos dados é a annotation @Valid. Ela é usada no momento que se quer realizar a validação.
        *
        *   Um DTO diferente será criado para atender a cada tipo de funcionalidade específica.
        *       Essa classe terá todos os campos da entidade Produto pois serve apenas para exemplo.
        *
        *   Caso um campo seja criado com um valor que viola sua constraint a exception 'MethodArgumentNotValidException' vai estourar.
         * */
public class ProductDTO implements Serializable {

    private Long id;

    @Size(min = 3, max = 80, message = "Name must have between 3 and 80 characters.") //    @Size especifica parametros de tamanho para o nome.
    @NotBlank(message = "Name must not be empty.") //   @NotBlank não permite que o nome seja nulo, vazio, ou seja, composto apenas de espaços em branco.
    private String name;
    private String description;

    @Positive(message = "Price must be a positive value.") //    @Positive garante que o numero seja maior que zero (positivo).
    private Double price;
    private String imgUrl;

    public ProductDTO() {
    }

    public ProductDTO(Product p) {
        id = p.getId();
        name = p.getName();
        description = p.getDescription();
        price = p.getPrice();
        imgUrl = p.getImgUrl();
    }

    /*
    * Se esse objeto for usado apenas para LEITURA de dados e fornecer respostas as requisiçoes feitas para essa API, não é necessários metodos SETTERS.
    * */

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
