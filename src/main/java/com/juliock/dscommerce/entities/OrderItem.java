package com.juliock.dscommerce.entities;

import jakarta.persistence.*;

import java.io.Serializable;

/*  O relacionamento da entidade Order com Product é de *muitos para muitos*, ou seja,
 *       várias instâncias de Order podem conter o mesmo Product, e várias instancias de Product
 *       podem estar contidas em uma mesma Order. Relacionamento (n,n).
 *
 *  Nesses caso, os relacionamentos geram uma tabela intermediária que contém as chaves primárias das duas entidades.
 *      O JPA se encarrega de criar essa tabela intermediária.
 *      Porém, a intenção aqui é que cada pedido feito armazene também a quantidade de produtos e o preço desse produto.
 *      Dessa forma temos que criar uma entidade pra conter esses dados adicionais: OrderItem.
 *      A relação que antes era (n,n) será quebrada em: Product(1) --------- (n)OrderItem(n) --------- (1)Order.
 *
 *  Agora uma chave composta será representada pela classe OrderItemPK.
 *      OrderItemPK vai conter a PK de Order e de Product.
 *
 */

@Entity
@Table(name = "tb_order_item")
public class OrderItem implements Serializable {

    @EmbeddedId
    private OrderItemPK id = new OrderItemPK();

    @ManyToOne
    @JoinColumn(name = "order_id")
    @MapsId("orderId")               //O JPA busca na instancia de Order o campo marcado com @Id e atribui o valor dele ao campo "orderId" da classe marcada como @Embeddable (OrderItemPK no caso)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @MapsId("productId")
    private Product product;

    private Integer quantity;
    private Double price;

    public OrderItem() {
    }

    public OrderItem(Order order, Product product, Integer quantity, Double price) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderItemPK getId() {
        return id;
    }

    private void setId(OrderItemPK id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
