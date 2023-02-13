package orders;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class OrderRepositoryTest {

    OrderRepository orderRepository;

    @BeforeEach
    void init() {
        MariaDbDataSource dataSource = new MariaDbDataSource();
        try {
            dataSource.setUrl("jdbc:mariadb://localhost:3306/employees?useUnicode=true");
            dataSource.setUser("employees");
            dataSource.setPassword("employees");
        } catch (SQLException se) {
            throw new IllegalStateException("Cannot connect!", se);
        }

        Flyway flyway = Flyway.configure().cleanDisabled(false).dataSource(dataSource).load();

        flyway.clean();
        flyway.migrate();

        orderRepository = new OrderRepository(dataSource);
    }

    @Test
    void testSaveOrder() {
        long id1 = orderRepository.saveOrder(new Order("Laptop", 2, 2000));
        assertNotEquals(0, id1);

        Long id2 = orderRepository.saveOrder(new Order("Laptop", 2, 2000));
        assertNotNull(id2);

        assertNotEquals(id1, id2);
    }
}