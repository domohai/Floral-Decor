package com.domohai.floral.service.order;

import com.domohai.floral.dto.OrderDTO;
import com.domohai.floral.dto.OrderItemDTO;
import com.domohai.floral.exception.ResourceNotFoundException;
import com.domohai.floral.model.Cart;
import com.domohai.floral.model.CartItem;
import com.domohai.floral.model.Order;
import com.domohai.floral.model.OrderItem;
import com.domohai.floral.model.Product;
import com.domohai.floral.repo.OrderRepository;
import com.domohai.floral.service.cart.ICartService;
import com.domohai.floral.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final IProductService productService;
    private final ICartService cartService;
    
    @Autowired
    public OrderService(OrderRepository orderRepository, IProductService productService, ICartService cartService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.cartService = cartService;
    }
    
    @Override
    public OrderDTO getOrderById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + id + " not found"));
        return convertToDTO(order);
    }
    
    @Override
    public List<OrderDTO> getOrdersByUserId(Integer userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(this::convertToDTO)
                .toList();
    }
    
    @Override
    public OrderDTO createOrder(Integer userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrderFromCart(cart);
        order.setUser(cart.getUser());
        order.setCreatedAt(LocalDateTime.now());
//        cartService.clearCart(cart.getId());
        Order savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }
    
    private Order createOrderFromCart(Cart cart) {
        Set<CartItem> items = cart.getCartItems();
        Set<OrderItem> orderItems = new HashSet<>();
        Order order = new Order();
        // Convert CartItem to OrderItem
        for (CartItem item : items) {
            OrderItem orderItem = new OrderItem();
            Product product = item.getProduct();
            productService.updateStock(product.getId(), product.getStock() - item.getQuantity());
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setUnitPrice(product.getPrice());
            orderItem.setTotalPrice();
            orderItems.add(orderItem);
            orderItem.setOrder(order);
        }
        order.setTotalPrice(cart.getTotalPrice());
        order.setItems(orderItems);
        return order;
    }
    
    private OrderDTO convertToDTO(Order order) {
        List<OrderItem> orderItems = order.getItems().stream().toList();
        // Convert OrderItem to OrderItemDTO
        List<OrderItemDTO> orderItemDTOs = orderItems.stream()
                .map(orderItem -> new OrderItemDTO(orderItem.getId(), orderItem.getProduct().getId(), orderItem.getQuantity(), orderItem.getUnitPrice(), orderItem.getTotalPrice(), orderItem.getProduct().getName()))
                .toList();
        
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setStatus(order.getStatus().name());
        orderDTO.setCreatedAt(order.getCreatedAt());
        orderDTO.setItems(orderItemDTOs);
        return orderDTO;
    }
}
