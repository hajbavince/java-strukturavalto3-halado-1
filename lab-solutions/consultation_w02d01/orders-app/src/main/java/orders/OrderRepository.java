package orders;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class OrderRepository {

    private JdbcTemplate jdbcTemplate;

    public OrderRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public long saveOrder(Order order) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement("insert into orders (product_name,product_count, price_per_product) values(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, order.getProductName());
            ps.setInt(2, order.getProductCount());
            ps.setInt(3, order.getPricePerProduct());
            return ps;
        }, holder);

        return holder.getKey().longValue();
    }

    public List<Order> getOrders() {
        return jdbcTemplate.query(
                "select * from orders order by product_name",
                (rs, rn) -> new Order(rs.getLong("id"), rs.getString("product_name"), rs.getInt("product_count"), rs.getInt("price_per_product")));
    }

    public List<Order> getOrdersOverLimitedOrderPrice(int limit) {
        return jdbcTemplate.query(
                "select * from orders where (product_count*price_per_product) > ?",
                (rs, rn) -> new Order(rs.getLong("id"), rs.getString("product_name"), rs.getInt("product_count"), rs.getInt("price_per_product")),
                limit);
    }

    public void clearTable() {
        jdbcTemplate.execute("delete from orders where 1=1");
    }

    public void createTableOrders(){

        jdbcTemplate.execute("create table orders\n" +
                "(\n" +
                "    id                  bigint auto_increment,\n" +
                "    product_name         varchar(255),\n" +
                "    product_count           bigint,\n" +
                "    price_per_product     bigint,\n" +
                "\n" +
                "    constraint pk_orders primary key (id)\n" +
                ")");

    }


}

