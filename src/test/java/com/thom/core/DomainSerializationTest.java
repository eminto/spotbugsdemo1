package com.thom.core;

import com.thom.core.domain.Order;
import com.thom.core.domain.OrderItem;
import com.thom.core.domain.User;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that all domain objects can actually be serialized and deserialized.
 *
 * This is the runtime complement to the SpotBugs static analysis: SpotBugs
 * checks the class structure at compile time; these tests prove the contract
 * holds at runtime.
 */
class DomainSerializationTest {

    @Test
    void user_roundTripsSerializationSuccessfully() throws Exception {
        User original = new User(1L, "alice", "alice@example.com");

        User copy = serializeAndDeserialize(original);

        assertEquals(original.getId(),       copy.getId());
        assertEquals(original.getUsername(), copy.getUsername());
        assertEquals(original.getEmail(),    copy.getEmail());
        assertEquals(original.getRegisteredOn(), copy.getRegisteredOn());
    }

    @Test
    void orderItem_roundTripsSerializationSuccessfully() throws Exception {
        OrderItem original = new OrderItem(10L, "Widget A", 3, new BigDecimal("14.99"));

        OrderItem copy = serializeAndDeserialize(original);

        assertEquals(original.getId(),          copy.getId());
        assertEquals(original.getProductName(), copy.getProductName());
        assertEquals(original.getQuantity(),    copy.getQuantity());
        assertEquals(0, original.getUnitPrice().compareTo(copy.getUnitPrice()));
    }

    @Test
    void order_withItemsAndCustomer_roundTripsSuccessfully() throws Exception {
        User customer = new User(2L, "bob", "bob@example.com");
        Order original = new Order(100L, customer);
        original.addItem(new OrderItem(1L, "Gadget B", 1, new BigDecimal("49.99")));
        original.addItem(new OrderItem(2L, "Widget A", 2, new BigDecimal("14.99")));

        Order copy = serializeAndDeserialize(original);

        assertEquals(original.getId(), copy.getId());
        assertEquals(original.getCustomer().getUsername(), copy.getCustomer().getUsername());
        assertEquals(0, original.getTotalAmount().compareTo(copy.getTotalAmount()));
        assertEquals(2, copy.getItems().size());
    }

    @SuppressWarnings("unchecked")
    private <T extends Serializable> T serializeAndDeserialize(T obj) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(obj);
        }
        try (ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(baos.toByteArray()))) {
            return (T) ois.readObject();
        }
    }
}
