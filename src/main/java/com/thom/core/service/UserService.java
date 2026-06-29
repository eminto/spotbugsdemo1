package com.thom.core.service;

import com.thom.core.domain.Order;
import com.thom.core.domain.OrderItem;
import com.thom.core.domain.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Plain Java service class. Intentionally does NOT implement Serializable.
 * When held as a field in a Serializable class (AppBeanImpl, PageBeanImpl),
 * SpotBugs raises SE_BAD_FIELD.
 */
public class UserService {

    private final AtomicLong idSequence = new AtomicLong(1);
    private final List<User> store = new ArrayList<>();

    public UserService() {
        store.add(new User(idSequence.getAndIncrement(), "alice", "alice@example.com"));
        store.add(new User(idSequence.getAndIncrement(), "bob",   "bob@example.com"));
    }

    public List<User> findAll() {
        return new ArrayList<>(store);
    }

    public User findById(Long id) {
        return store.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idSequence.getAndIncrement());
            store.add(user);
        }
        return user;
    }

    public Order createSampleOrder(User customer) {
        Order order = new Order(idSequence.getAndIncrement(), customer);
        order.addItem(new OrderItem(idSequence.getAndIncrement(), "Widget A", 2,
                new BigDecimal("14.99")));
        order.addItem(new OrderItem(idSequence.getAndIncrement(), "Gadget B", 1,
                new BigDecimal("49.99")));
        return order;
    }
}
