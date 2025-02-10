package com.domohai.floral.service.cart;

import com.domohai.floral.exception.ResourceNotFoundException;
import com.domohai.floral.model.Cart;
import com.domohai.floral.model.CartItem;
import com.domohai.floral.repo.CartItemRepository;
import com.domohai.floral.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final IProductService productService;
    
    @Autowired
    public CartItemService(CartItemRepository cartItemRepository, IProductService productService) {
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }
    
    @Override
    public void addItemToCart(Cart cart, Integer productId, Integer quantity) {
        // check if the product is already in the cart
        // if it is, update the quantity
        // if it is not, add a new cart item to the cart
        CartItem item = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            item = new CartItem();
            item.setProduct(productService.getProductById(productId));
            item.setQuantity(quantity);
            item.setCart(cart);
            // cascade = CascadeType.ALL will save the cart item to the database
            cart.addCartItem(item);
        }
    }

    @Override
    public void removeItemFromCart(Cart cart, Integer cartItemId) {
        CartItem item = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item to be removed not found with id: " + cartItemId));
        // orphanRemoval = true will delete the cart item from the database
        // when it is removed from the cart items list
        cart.removeCartItem(item);
    }

    @Override
    public void updateItemQuantity(Integer cartId, Integer cartItemId, Integer quantity) {
        // This is not required for the current implementation
    }
}
