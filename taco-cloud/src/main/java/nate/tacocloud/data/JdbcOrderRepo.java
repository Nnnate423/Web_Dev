package nate.tacocloud.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import nate.tacocloud.Order;
import nate.tacocloud.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcOrderRepo implements OrderRepo {

    private SimpleJdbcInsert OrderInserter;
    private SimpleJdbcInsert TacoOrderInserter;
    private ObjectMapper objmap;
    long orderId;

    @Autowired
    public JdbcOrderRepo (JdbcTemplate jdbc){
        this.OrderInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order")
                .usingGeneratedKeyColumns("id");
        this.TacoOrderInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order_Tacos");
        this.objmap = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {
        order.setPlacedAt(new Date());
        long orderId = saveOrderDetails(order);
        order.setId(orderId);
        List<Taco> tacos = order.getTacos();
        for (Taco taco: tacos){
            saveTacoToOrder(taco);
        }
        return order;
    }

    private void saveTacoToOrder(Taco taco) {
        Map<String, Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());
        TacoOrderInserter.execute(values);
    }

    private long saveOrderDetails(Order order) {
        @SuppressWarnings("unchecked")
        Map<String, Object> values =
                objmap.convertValue(order, Map.class);
        values.put("placedAt", order.getPlacedAt());
        orderId = OrderInserter
                        .executeAndReturnKey(values)
                        .longValue();
        return orderId;
    }
}
