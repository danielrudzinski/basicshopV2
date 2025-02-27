package pl.myproject.basicshop.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;



@Entity
@Table(name="order_items")
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;;
    private Integer quantity ;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Products products;

    public OrderItems() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


}
