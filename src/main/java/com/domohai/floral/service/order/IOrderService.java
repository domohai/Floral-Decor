package com.domohai.floral.service.order;

import com.domohai.floral.model.Order;

import java.util.List;

public interface IOrderService {
    Order getOrderById(Integer id);
    Order createOrder(Integer userId);
    List<Order> getOrdersByUserId(Integer userId);
}
