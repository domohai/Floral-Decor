package com.domohai.floral.service.cart;

import com.domohai.floral.model.Cart;

public interface ICartItemService {
    void addItemToCart(Cart cart, Integer productId, Integer quantity);
    void removeItemFromCart(Cart cart, Integer cartItemId);
    void updateItemQuantity(Integer cartItemId, Integer quantity);
}
