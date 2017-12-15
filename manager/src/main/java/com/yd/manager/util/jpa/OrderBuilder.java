package com.yd.manager.util.jpa;

import javax.persistence.criteria.Order;
import java.util.List;

@FunctionalInterface
public interface OrderBuilder {
    List<Order> getOrders();
}
