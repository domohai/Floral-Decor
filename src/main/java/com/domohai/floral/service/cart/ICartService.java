package com.domohai.floral.service.cart;

import com.domohai.floral.dto.CartDTO;
import com.domohai.floral.model.Cart;
import com.domohai.floral.model.User;

public interface ICartService {
    // Only create cart when there is a new user
    CartDTO createCart(User user);
    CartDTO getCartById(Integer id);
    CartDTO getCartByUserId(Integer userId);
    void clearCart(Integer id);
    void addItemToCart(Integer cartId, Integer productId, Integer quantity);
    void removeItemFromCart(Integer cartId, Integer cartItemId);
    void updateCart(Cart cart);
    void updateItemQuantity(Integer cartId, Integer cartItemId, Integer quantity);
}
