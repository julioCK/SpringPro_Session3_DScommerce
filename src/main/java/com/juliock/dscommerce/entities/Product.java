package com.juliock.dscommerce.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
    private Double price;
    private String imgUrl;

    /*  @ManyToMany sinaliza que *varias* instancias dessa entidade estão relacionadas a varias instancias de outra entidade.
            Esse tipo de relacionamento gera uma tabela separada de referencia contendo a chave primaria de cada uma das duas entidades */

    /*  @JoinTable sinaliza para o JPA que a tabela de relacionamento (JoinTable) será criada com o nome especificado
    *       e vai conter uma coluna (JoinColumns) para a FK da entidade Product (coluna "product_id")
    *       e um coluna (inverseJoinColumns) para a FK da entidade Category (coluna "category_id").   */

    /*  Não pode haver um produto em duas categorias iguais (repetidas), portanto a interface Set é a ideal para agrupar categorias pois nao admite repetições. */

    @ManyToMany
    @JoinTable(name = "tb_product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<OrderItem> items = new HashSet<>();

    public Product() {
    }

    public Product(Long id, String name, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public Product(Long id, String name, String description, Double price, String imgUrl, Set<OrderItem> orderItems) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.items = orderItems;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Set<Category> getCategories() {
        return new HashSet<>(this.categories);
    }

    public Set<OrderItem> getItems() {
        return new HashSet<>(this.items);
    }

    public List<Order> getOrders() {
        return items.stream().map(OrderItem::getOrder).toList();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
