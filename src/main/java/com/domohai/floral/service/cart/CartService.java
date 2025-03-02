package com.domohai.floral.service.cart;

import com.domohai.floral.dto.CartDTO;
import com.domohai.floral.exception.ResourceNotFoundException;
import com.domohai.floral.model.Cart;
import com.domohai.floral.model.User;
import com.domohai.floral.repo.CartRepository;
import com.domohai.floral.utils.CartUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final ICartItemService cartItemService;
    
    @Autowired
    public CartService(CartRepository cartRepository, ICartItemService cartItemService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
    }
    
    @Override
    public CartDTO createCart(User user) {
        Optional<Cart> existingCart = cartRepository.findByUserId(user.getId());
        if (existingCart.isPresent()) {
            // return the existing cart if it exists
            return CartUtils.convertToCartDTO(existingCart.get());
        }
        // create a new cart if it does not exist
        Cart cart = new Cart();
        cart.setUser(user);
        return CartUtils.convertToCartDTO(cartRepository.save(cart));
    }
    
    @Override
    public CartDTO getCartById(Integer id) {
        Cart cart =  cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + id));
        cart.updateTotalPrice();
        cartRepository.save(cart);
        return CartUtils.convertToCartDTO(cart);
    }
    
    @Override
    public CartDTO getCartByUserId(Integer userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user with id: " + userId));
//        cart.updateTotalPrice();
//        cartRepository.save(cart);
        return CartUtils.convertToCartDTO(cart);
    }
    
    @Override
    public void updateCart(Cart cart) {
        cartRepository.save(cart);
    }
    
    @Override
    public void updateItemQuantity(Integer cartId, Integer cartItemId, Integer quantity) {
        // delegate the logic to the cart item service
        cartItemService.updateItemQuantity(cartItemId, quantity);
        cartRepository.save(cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + cartId)));
    }
    
    @Override
    public void addItemToCart(Integer cartId, Integer productId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + cartId));
        // delegate the logic to the cart item service
        cartItemService.addItemToCart(cart, productId, quantity);
        cartRepository.save(cart);
    }
    
    @Override
    public void clearCart(Integer cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + cartId));
        // clear cart items in memory
        // orphanRemoval = true will delete the cart items from the database
        cart.getCartItems().clear();
        cart.updateTotalPrice();
        cartRepository.save(cart);
    }
    
    @Override
    public void removeItemFromCart(Integer cartId, Integer cartItemId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + cartId));
        // delegate the logic to the cart item service
        cartItemService.removeItemFromCart(cart, cartItemId);
        cartRepository.save(cart);
    }
}
