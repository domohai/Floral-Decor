package com.domohai.floral.old.service.service.order;

import com.domohai.floral.old.dto.OrderDTO;

import java.util.List;

public interface IOrderService {
    OrderDTO getOrderById(Integer id);
    OrderDTO createOrder(Integer userId);
    List<OrderDTO> getOrdersByUserId(Integer userId);
}
