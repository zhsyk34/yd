package com.yd.manager.utils.jpa;

import javax.persistence.criteria.Order;
import java.util.List;

@FunctionalInterface
public interface OrderBuilder {
    List<Order> getOrders();
}
