package com.juliock.dscommerce.dto;


import com.juliock.dscommerce.entities.Product;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/*
        *   Não é adequado atender às requisições fornecendo objetos contendo TODOS os dados das entidades do banco.
        *   O ideal é extrair uma PROJEÇÃO dos dados da entidade, de acordo com a necessidade da requisição.
        *       Para realizar essa projeção dos dados de objetos recuperados do banco de dados é usada um objeto DTO (Data Transfer Object);
        *
        *   Um DTO diferente será criado para atender a cada tipo de funcionalidade específica.
        *       Essa classe terá todos os campos da entidade Produto pois serve apenas para exemplo.
         * */
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
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
