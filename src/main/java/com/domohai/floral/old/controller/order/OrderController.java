package com.domohai.floral.old.controller.order;

import com.domohai.floral.old.controller.ApiResponse;
import com.domohai.floral.old.dto.OrderDTO;
import com.domohai.floral.old.exception.ResourceNotFoundException;
import com.domohai.floral.old.service.service.order.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final IOrderService orderService;
    
    @Autowired
    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getOrderById(
            @PathVariable(name = "id") Integer id
    ) {
        try {
            OrderDTO orderDto = orderService.getOrderById(id);
            return ResponseEntity.ok(new ApiResponse("Order retrieved successfully", orderDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    
    // Get orders by user id
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getOrdersByUserId(
            @PathVariable(name = "userId") Integer userId
    ) {
        try {
            List<OrderDTO> orders = orderService.getOrdersByUserId(userId);
            return ResponseEntity.ok(new ApiResponse("Orders retrieved successfully", orders));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    
    @PostMapping("/create/user/{userId}")
    public ResponseEntity<ApiResponse> createOrder(
            @PathVariable(name = "userId") Integer userId
    ) {
        try {
            OrderDTO orderDto = orderService.createOrder(userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Order created successfully", orderDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
