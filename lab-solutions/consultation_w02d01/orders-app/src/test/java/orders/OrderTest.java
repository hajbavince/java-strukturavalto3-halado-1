package orders;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testCreateWithoutId() {
        Order order = new Order("laptop", 1, 1000);

        assertEquals("laptop", order.getProductName());
        assertEquals(1, order.getProductCount());
        assertEquals(1000, order.getPricePerProduct());
    }

    @Test
    void testCreateWithId() {
        Order order = new Order(2L, "laptop", 1, 1000);

        assertEquals(2L, order.getId());
        assertEquals("laptop", order.getProductName());
        assertEquals(1, order.getProductCount());
        assertEquals(1000, order.getPricePerProduct());
    }
}