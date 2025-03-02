package com.domohai.floral.controller.cart;

import com.domohai.floral.controller.ApiResponse;
import com.domohai.floral.dto.CartDTO;
import com.domohai.floral.exception.ResourceNotFoundException;
import com.domohai.floral.service.cart.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
public class CartController {
    private final ICartService cartService;
    
    @Autowired
    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }
    
    // GET
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCartById(
            @PathVariable(name = "id") Integer id
    ) {
        CartDTO cart = null;
        try {
            cart = cartService.getCartById(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Cart not found with id: " + id, null));
        }
        return ResponseEntity.ok(new ApiResponse("Cart found with id: " + id, cart));
    }
    
    // POST
    @PostMapping("/{cartId}/add-item")
    public ResponseEntity<ApiResponse> addItemToCart(
            @RequestParam(name = "productId", required = true) Integer productId,
            @RequestParam(name = "quantity", required = true) Integer quantity,
            @PathVariable(name = "cartId") Integer cartId
    ) {
        try {
            cartService.addItemToCart(cartId, productId, quantity);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Cart not found with id: " + cartId, null));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Successully add item to cart", null));
    }
    
    // PUT
    // Update cart item quantity
    @PutMapping("/{cartId}/item/{itemId}")
    public ResponseEntity<ApiResponse> updateItemQuantity(
            @RequestParam(name = "quantity", required = true) Integer quantity,
            @PathVariable(name = "cartId") Integer cartId,
            @PathVariable(name = "itemId") Integer itemId
    ) {
        try {
            cartService.updateItemQuantity(cartId, itemId, quantity);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Cart item not found with id: " + itemId, null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successully update item quantity with id: " + itemId, null));
    }
    
    // DELETE
    // Delete cart item by id
    @DeleteMapping("/{cartId}/item/{itemId}")
    public ResponseEntity<ApiResponse> deleteItemFromCart(
            @PathVariable(name = "cartId") Integer cartId,
            @PathVariable(name = "itemId") Integer itemId
    ) {
        try {
            cartService.removeItemFromCart(cartId, itemId);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Cart item not found with id: " + itemId, null));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("Successully delete item with id: " + itemId, null));
    }
    
    // Clear cart
    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(
            @PathVariable(name = "cartId") Integer cartId
    ) {
        try {
            cartService.clearCart(cartId);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Cart not found with id: " + cartId, null));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("Successully clear cart with id: " + cartId, null));
    }
}
