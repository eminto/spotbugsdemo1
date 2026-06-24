package gov.nystax.ecore.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Domain object representing a single line item within an Order.
 *
 * SE_BAD_FIELD DEMO: the 'product' field references Product, which does NOT
 * implement Serializable. SpotBugs will flag this and fail the build.
 */
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String productName;
    private int quantity;
    private BigDecimal unitPrice;

    /*
     * SE_BAD_FIELD: Product is not Serializable.
     * SpotBugs will fail the build here — domain classes are not in the exclude filter.
     * Fix: make Product implement Serializable and declare serialVersionUID.
     */
    private Product product;

    public OrderItem() {}

    public OrderItem(Long id, String productName, int quantity, BigDecimal unitPrice) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public OrderItem(Long id, Product product, int quantity, BigDecimal unitPrice) {
        this.id = id;
        this.product = product;
        this.productName = product.getName();
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public BigDecimal getLineTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    @Override
    public String toString() {
        return "OrderItem{product='" + productName + "', qty=" + quantity
                + ", unitPrice=" + unitPrice + '}';
    }
}
