package com.thom.core.page;

import com.thom.core.domain.Order;
import com.thom.core.domain.Product;
import com.thom.core.domain.User;
import com.thom.core.service.UserService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of PageBean.
 *
 * SpotBugs findings demonstrated here:
 *
 *   SE_BAD_FIELD
 *     userService is not Serializable. Any attempt to serialize a PageBeanImpl
 *     instance will throw NotSerializableException at runtime.
 *
 *   SE_BAD_FIELD
 *     featuredProducts is typed as List<Product>. Product is not Serializable,
 *     so storing Product instances in this field causes a serialization failure.
 */
public class PageBeanImpl implements PageBean, Serializable {

    private static final long serialVersionUID = 1L;

    // SE_BAD_FIELD: UserService does not implement Serializable
    private UserService userService;

    private User selectedUser;
    private Order currentOrder;
    private String statusMessage;

    // SE_BAD_FIELD: Product (element type) does not implement Serializable
    private List<Product> featuredProducts = new ArrayList<>();

    public PageBeanImpl() {
        this.userService = new UserService();
    }

    @Override
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @Override
    public String selectUser(Long userId) {
        this.selectedUser = userService.findById(userId);
        this.statusMessage = "Selected: " + selectedUser.getUsername();
        return "user-detail";
    }

    @Override
    public String placeOrder() {
        if (selectedUser == null) {
            statusMessage = "No user selected.";
            return null;
        }
        currentOrder = userService.createSampleOrder(selectedUser);
        statusMessage = "Order placed: " + currentOrder.getId();
        return "order-confirm";
    }

    @Override
    public User getSelectedUser() { return selectedUser; }

    @Override
    public Order getCurrentOrder() { return currentOrder; }

    @Override
    public String getStatusMessage() { return statusMessage; }

    @Override
    public void setStatusMessage(String statusMessage) { this.statusMessage = statusMessage; }

    public List<Product> getFeaturedProducts() { return featuredProducts; }

    public void addFeaturedProduct(Product product) {
        featuredProducts.add(product);
    }
}
