package gov.nystax.ecore.service;

import gov.nystax.ecore.domain.Order;
import gov.nystax.ecore.domain.OrderItem;
import gov.nystax.ecore.domain.User;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Application-scoped CDI service bean.
 *
 * Intentionally does NOT implement Serializable – it is an ApplicationScoped bean
 * managed by the container and is never passivated.
 *
 * When PageBean (SessionScoped) holds a reference to this bean via @Inject,
 * SpotBugs flags SE_BAD_FIELD because UserService is not Serializable.
 * That warning is suppressed via spotbugs/findbugs.xml for PageBean.
 */
@ApplicationScoped
public class UserService {

    private final AtomicLong idSequence = new AtomicLong(1);
    private final List<User> store = new ArrayList<>();

    public UserService() {
        // Seed some demo data
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
