package com.domohai.floral.utils;

import com.domohai.floral.dto.CartDTO;
import com.domohai.floral.dto.CartItemDTO;
import com.domohai.floral.model.Cart;
import com.domohai.floral.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartUtils {
    public static CartDTO convertToCartDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setUserId(cart.getUser().getId());
        cartDTO.setTotalPrice(cart.getTotalPrice());
        List<CartItemDTO> cartItemDTOs = new ArrayList<>();
        List<CartItem> cartItems = cart.getCartItems().stream().toList();
        for (CartItem cartItem : cartItems) {
            CartItemDTO cartItemDTO = new CartItemDTO();
            cartItemDTO.setQuantity(cartItem.getQuantity());
            cartItemDTO.setProductId(cartItem.getProduct().getId());
            cartItemDTO.setUnitPrice(cartItem.getUnitPrice());
            cartItemDTO.setTotalPrice(cartItem.getTotalPrice());
            cartItemDTO.setProductName(cartItem.getProduct().getName());
            cartItemDTOs.add(cartItemDTO);
        }
        cartDTO.setCartItems(cartItemDTOs);
        return cartDTO;
    }
}
