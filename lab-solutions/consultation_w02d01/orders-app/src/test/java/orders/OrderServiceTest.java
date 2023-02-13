package orders;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderService orderService = new OrderService(orderRepository);

    @Test
    void testSaveOrder() {
        when(orderRepository.saveOrder(any())).thenReturn(2L);
        long id = orderService.saveOrder(new Order("Laptop", 2, 2000));

        verify(orderRepository).saveOrder(any());
        assertEquals(2L, id);
    }

    @Test
    void testSaveOrderAndDontReturnGeneratedKeys() {
        Order order = new Order("Laptop", 2, 2000);
        orderService.saveOrderAndDontReturnGeneratedKeys(order);

        verify(orderRepository).saveOrder(order);
    }

    @Test
    void testGetOrders() {
        orderService.getOrders();

        verify(orderRepository).getOrders();
    }

    @Test
    void testGetOrdersOverLimitedOrderPrice() {
        when(orderRepository.getOrdersOverLimitedOrderPrice(anyInt())).thenReturn(List.of(
                new Order("Mobile", 4, 800),
                new Order("Laptop", 2, 1200),
                new Order("Tv", 1, 150),
                new Order("Hairdrier", 2, 200)
        ));
        List<Order> orders = orderService.getOrdersOverLimitedOrderPrice(20000);

        assertThat(orders)
                .hasSize(4)
                .extracting(Order::getProductName)
                .containsExactly("Mobile", "Laptop", "Tv", "Hairdrier");
    }

    @Test
    void testGetOrdersOverLimitedOrderPriceException() {
        when(orderRepository.getOrdersOverLimitedOrderPrice(anyInt())).thenReturn(List.of());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> orderService.getOrdersOverLimitedOrderPrice(2));
        assertEquals("Too high limit", ex.getMessage());
    }

    @Test
    void testCollectProductsAndCount() {
        when(orderRepository.getOrders()).thenReturn(List.of(
                new Order("Mobile", 1, 800),
                new Order("Laptop", 2, 1200),
                new Order("Mobile", 3, 1500),
                new Order("Hairdrier", 2, 200),
                new Order("Laptop", 4, 1200)
        ));

        Map<String, Integer> result = orderService.collectProductsAndCount();

        assertEquals(3, result.keySet().size());
        assertEquals(Set.of("Mobile", "Laptop", "Hairdrier"), result.keySet());
    }
}