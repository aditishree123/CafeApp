package com.dbms.cafe.repositories;

import com.dbms.cafe.models.Order;
import com.dbms.cafe.models.Product;

import java.util.List;

public interface OrderRepository {
    public void saveOrder(Order order);
    public void saveProduct(Product product);
    public Order findByOrderId(int Id);
    public List<Order> ByUserId(int Id);
    public List<Product> ByOrderId(int Id);
    public List<Product> ByOrderIdOffer(int Id);
    public List<Order> AllOrders();
    public Order findLast(int Id);
    public void setStatus(String status,int Id);
    public float AvgRating(int Id);
    public float AvgOfferRating(int Id);
    public void updateRating(int rate,int Id);
    public Product findProductById(int Id);
    public Product findProductByIdO(int Id);
}
