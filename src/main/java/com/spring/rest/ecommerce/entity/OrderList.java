package com.spring.rest.ecommerce.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_list")
public class OrderList {

    @Id
    @Column(name = "order_id")
    private long oderId;

    @Id
    @Column(name = "product_id")
    private long productId;

    @Column(name = "quantity")
    private int quantity;

    public long getOderId() {
        return oderId;
    }

    public void setOderId(long oderId) {
        this.oderId = oderId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
