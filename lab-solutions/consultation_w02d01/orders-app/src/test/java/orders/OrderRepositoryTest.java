package orders;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class OrderRepositoryTest {

    OrderRepository orderRepository;

    @BeforeEach
    void init() {
//        MariaDbDataSource dataSource = new MariaDbDataSource();
//        try {
//            dataSource.setUrl("jdbc:mariadb://localhost:3306/employees?useUnicode=true");
//            dataSource.setUser("employees");
//            dataSource.setPassword("employees");
//        } catch (SQLException se) {
//            throw new IllegalStateException("Cannot connect!", se);
//        }
//
//        Flyway flyway = Flyway.configure().cleanDisabled(false).dataSource(dataSource).load();
//
//        flyway.clean();
//        flyway.migrate();

        orderRepository = new OrderRepository(getMySQLDataSource());
        orderRepository.clearTable();
    }

    //@BeforeAll
    static void table() {
      new  OrderRepository(getMySQLDataSource()).createTableOrders();
    }


    public static DataSource getMySQLDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/employees?createDatabaseIfNotExist=true&serverTimezone=UTC&useSSL=false&allowMultiQueries=true");
        dataSource.setUsername("root");
        dataSource.setPassword("Test123!");
        return dataSource;
    }

    @Test
    void testSaveOrder() {
        long id1 = orderRepository.saveOrder(new Order("Laptop", 2, 2000));
        assertNotEquals(0, id1);

        Long id2 = orderRepository.saveOrder(new Order("Laptop", 2, 2000));
        assertNotNull(id2);

        assertNotEquals(id1, id2);
    }

    @Test
    void testGetOrders() {
        orderRepository.saveOrder(new Order("Mobile", 4, 800));
        orderRepository.saveOrder(new Order("Laptop", 2, 1200));
        orderRepository.saveOrder(new Order("Tv", 1, 150));
        orderRepository.saveOrder(new Order("Hairdrier", 2, 200));

        List<Order> orders = orderRepository.getOrders();

        assertThat(orders)
                .hasSize(4)
                .extracting(Order::getProductName)
                .containsExactly("Hairdrier", "Laptop", "Mobile", "Tv");
    }

    @ParameterizedTest
    @MethodSource("createArguments")
    public void testGetOrdersOverLimitedOrderPrice(int limit, int piecesOfOrders) {
        orderRepository.saveOrder(new Order("Mobile", 4, 800));
        orderRepository.saveOrder(new Order("Laptop", 2, 1200));
        orderRepository.saveOrder(new Order("Tv", 1, 150));
        orderRepository.saveOrder(new Order("Hairdrier", 2, 200));

        assertEquals(piecesOfOrders, orderRepository.getOrdersOverLimitedOrderPrice(limit).size());
    }

    static Stream<Arguments> createArguments() {
        return Stream.of(
                arguments(4000, 0),
                arguments(3000, 1),
                arguments(2000, 2),
                arguments(300, 3),
                arguments(100, 4)
        );
    }
}