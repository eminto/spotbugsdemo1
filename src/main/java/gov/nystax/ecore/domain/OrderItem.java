package gov.nystax.ecore.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Domain object representing a single line item within an Order.
 *
 * All fields are Serializable primitive wrappers or BigDecimal –
 * SpotBugs SE_BAD_FIELD will not fire here.
 */
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String productName;
    private int quantity;
    private BigDecimal unitPrice;

    public OrderItem() {}

    public OrderItem(Long id, String productName, int quantity, BigDecimal unitPrice) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public BigDecimal getLineTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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
