package com.app.service;

import java.util.List;


import com.app.dto.CartRequestDTO;
import com.app.dto.CartResponseDTO;
import com.app.entity.CartItem;

public interface CartService {
	  CartResponseDTO addToCart(CartRequestDTO dto);

    void decreaseQuantity(Long cartItemId);

    List<CartResponseDTO> getCartByUser(Long userId);

	    void removeItem(Long cartItemId);

    public void clearCart(Long userId);

}
