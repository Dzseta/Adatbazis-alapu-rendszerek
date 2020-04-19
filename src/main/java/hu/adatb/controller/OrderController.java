package hu.adatb.controller;

import hu.adatb.dao.OrderDAO;
import hu.adatb.dao.OrderDAOImpl;
import hu.adatb.model.Order;

import java.util.List;

public class OrderController {

    private OrderDAO dao = new OrderDAOImpl();

    public OrderController(){ };

    public boolean foreignKey(Order order) { return dao.foreignKey(order); };

    public boolean add(Order order){
        return dao.add(order);
    }

    public List<Order> list(){
        return dao.list();
    }
}
