package com.domohai.floral.old.dto;

import java.math.BigDecimal;

public class CartItemDTO {
    private Integer quantity;
    private Integer productId;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private String productName;
    
    public CartItemDTO() {
    }
    
    public CartItemDTO(Integer quantity, Integer productId, BigDecimal unitPrice, BigDecimal totalPrice, String productName) {
        this.quantity = quantity;
        this.productId = productId;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.productName = productName;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public Integer getProductId() {
        return productId;
    }
    
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
}
