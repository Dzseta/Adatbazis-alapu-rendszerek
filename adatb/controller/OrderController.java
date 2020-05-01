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

    public List<Order> list(String email){
        return dao.list(email);
    }

    public boolean delete(Order order){
        return dao.delete(order);
    }

    public boolean quantityNumber(Order order, int id) { return dao.quantityNumber(order, id); };

    public int getSalesPerCity(String name){
        return dao.getSalesPerCity(name);
    }

    public double getAvgBookSales(int isbn){
        return dao.getAvgBookSales(isbn);
    }
}
