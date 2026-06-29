package com.thom.core.domain;

import java.math.BigDecimal;

/**
 * Domain class representing a product.
 *
 * INTENTIONALLY does NOT implement java.io.Serializable.
 *
 * SpotBugs detects this indirectly: OrderItem IS Serializable and holds a
 * Product field, triggering SE_BAD_FIELD. PageBeanImpl also holds a
 * List<Product>, which SpotBugs flags for the same reason.
 *
 * Fix: add "implements Serializable" and declare serialVersionUID.
 */
public class Product {

    private Long id;
    private String name;
    private String sku;
    private BigDecimal price;

    public Product() {}

    public Product(Long id, String name, String sku, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.sku = sku;
        this.price = price;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + name + "', sku='" + sku + "', price=" + price + '}';
    }
}
