package hu.adatb.dao;

import hu.adatb.model.Order;

import java.util.List;

public interface OrderDAO {
    boolean foreignKey(Order order);
    boolean add(Order order);
    List<Order> list();
}
