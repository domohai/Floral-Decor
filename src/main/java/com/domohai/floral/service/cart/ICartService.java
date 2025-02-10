package com.domohai.floral.service.cart;

import com.domohai.floral.model.Cart;
import com.domohai.floral.model.User;

public interface ICartService {
    // Only create cart when there is a new user
    Cart createCart(User user);
    Cart getCartById(Integer id);
    void clearCart(Integer id);
    void addItemToCart(Integer cartId, Integer productId, Integer quantity);
    void removeItemFromCart(Integer cartId, Integer cartItemId);
    void updateCart(Cart cart);
}
