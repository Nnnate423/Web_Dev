package nate.tacocloud.data;

import nate.tacocloud.Order;

public interface OrderRepo {
    Order save(Order order);
}
