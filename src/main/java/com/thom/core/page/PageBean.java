package com.thom.core.page;

import com.thom.core.domain.Order;
import com.thom.core.domain.User;

import java.util.List;

public interface PageBean {

    List<User> getAllUsers();

    String selectUser(Long userId);

    String placeOrder();

    User getSelectedUser();

    Order getCurrentOrder();

    String getStatusMessage();

    void setStatusMessage(String statusMessage);
}
