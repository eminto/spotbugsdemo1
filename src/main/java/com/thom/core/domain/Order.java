package com.thom.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Domain object representing a customer order.
 *
 * Serializable contract:
 *  - serialVersionUID declared
 *  - All fields are Serializable: String, BigDecimal, LocalDateTime, ArrayList<OrderItem>
 *  - OrderItem (nested type) is also Serializable
 */
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private User customer;           // User is Serializable – no SE_BAD_FIELD
    private LocalDateTime placedAt;
    private BigDecimal totalAmount;
    private List<OrderItem> items;   // ArrayList and OrderItem are both Serializable

    public Order() {
        this.items = new ArrayList<>();
        this.placedAt = LocalDateTime.now();
        this.totalAmount = BigDecimal.ZERO;
    }

    public Order(Long id, User customer) {
        this();
        this.id = id;
        this.customer = customer;
    }

    public void addItem(OrderItem item) {
        items.add(item);
        totalAmount = totalAmount.add(item.getLineTotal());
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getCustomer() { return customer; }
    public void setCustomer(User customer) { this.customer = customer; }

    public LocalDateTime getPlacedAt() { return placedAt; }

    public BigDecimal getTotalAmount() { return totalAmount; }

    public List<OrderItem> getItems() { return Collections.unmodifiableList(items); }

    @Override
    public String toString() {
        return "Order{id=" + id + ", customer=" + customer + ", total=" + totalAmount + '}';
    }
}
