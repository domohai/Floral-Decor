package com.domohai.floral.controller.order;

import com.domohai.floral.controller.ApiResponse;
import com.domohai.floral.exception.ResourceNotFoundException;
import com.domohai.floral.model.Order;
import com.domohai.floral.service.order.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            Order order = orderService.getOrderById(id);
            return ResponseEntity.ok(new ApiResponse("Order retrieved successfully", order));
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
            return ResponseEntity.ok(new ApiResponse("Orders retrieved successfully", orderService.getOrdersByUserId(userId)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createOrder(
            @RequestParam(name = "userId") Integer userId
    ) {
        try {
            Order order = orderService.createOrder(userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Order created successfully", order));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
