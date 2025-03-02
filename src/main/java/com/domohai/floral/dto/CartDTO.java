package com.domohai.floral.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartDTO {
    private Integer userId;
    private BigDecimal totalPrice;
    private List<CartItemDTO> cartItems;
    
    public CartDTO() {
    }
    
    public CartDTO(Integer userId, BigDecimal totalPrice, List<CartItemDTO> cartItems) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.cartItems = cartItems;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }
    
    public void setCartItems(List<CartItemDTO> cartItems) {
        this.cartItems = cartItems;
    }
}
