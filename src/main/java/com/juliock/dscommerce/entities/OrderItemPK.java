package com.juliock.dscommerce.entities;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/*  @Embeddable sinaliza que as instâncias dessa classe serão parte integrante de uma outra classe.
                                         *       Portanto a classe OrderItemPK, nesse caso, não é uma entidade independente, mas sim uma estrutura
                                         *       incorporada aos objetos da classe OrderItem.
                                         *       Os dados dessa classe serão persistidos no banco juntamente à tabela da classe em que está incorporada.
                                         */

@Embeddable
public class OrderItemPK implements Serializable {

    private Long orderId;
    private Long productId;

    public OrderItemPK() {
    }

    public OrderItemPK(Long orderId, Long productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemPK that = (OrderItemPK) o;
        return Objects.equals(getOrderId(), that.getOrderId()) && Objects.equals(getProductId(), that.getProductId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderId(), getProductId());
    }
}