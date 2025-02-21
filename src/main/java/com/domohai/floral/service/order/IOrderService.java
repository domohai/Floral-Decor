package com.domohai.floral.service.order;

import com.domohai.floral.dto.OrderDTO;
import com.domohai.floral.model.Order;

import java.util.List;

public interface IOrderService {
    OrderDTO getOrderById(Integer id);
    OrderDTO createOrder(Integer userId);
    List<OrderDTO> getOrdersByUserId(Integer userId);
}
