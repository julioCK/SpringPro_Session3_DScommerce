package com.juliock.dscommerce.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_category")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    /*  Várias instancias da entidade Category estarão relacionadas a várias instancias da entidade Product.
    *           A relação dessa entidade com Product está mapeada em nas entidades do tipo Product pelo atributo "categories"   */

    /*  Não pode haver dois produtos iguais (repetidos) listados em uma mesma categoria, portanto a interface Set é ideal aqui, visto que não admite repetições de produtos.    */

    @ManyToMany(mappedBy = "categories")
    private Set<Product> products = new HashSet<>();

    public Category() {
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
