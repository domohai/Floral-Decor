package com.domohai.floral.old.service.service.cart;

import com.domohai.floral.old.dto.CartDTO;
import com.domohai.floral.old.model.Cart;
import com.domohai.floral.old.model.User;

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
