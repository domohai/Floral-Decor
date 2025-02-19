package com.domohai.floral.service.order;

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
    public Order getOrderById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + id + " not found"));
    }
    
    @Override
    public List<Order> getOrdersByUserId(Integer userId) {
        return orderRepository.findAllByUserId(userId);
    }
    
    @Override
    public Order createOrder(Integer userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrderFromCart(cart);
        order.setUser(cart.getUser());
        order.setCreatedAt(LocalDateTime.now());
        cartService.clearCart(cart.getId());
        return orderRepository.save(order);
    }
    
    private Order createOrderFromCart(Cart cart) {
        Set<CartItem> items = cart.getCartItems();
        Set<OrderItem> orderItems = new HashSet<>();
        for (CartItem item : items) {
            OrderItem orderItem = new OrderItem();
            Product product = item.getProduct();
            productService.updateStock(product.getId(), product.getStock() - item.getQuantity());
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItems.add(orderItem);
        }
        Order order = new Order();
        order.setTotalPrice(cart.getTotalPrice());
        order.setItems(orderItems);
        return order;
    }
}
