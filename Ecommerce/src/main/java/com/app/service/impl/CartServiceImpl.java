package com.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dto.CartRequestDTO;
import com.app.dto.CartResponseDTO;
import com.app.entity.CartItem;
import com.app.entity.Product;
import com.app.entity.User;
import com.app.mapper.CartItemMapper;
import com.app.repository.CartItemRepository;
import com.app.repository.ProductRepository;
import com.app.repository.UserRepository;
import com.app.service.CartService;

@Service("cartService")
public class CartServiceImpl implements CartService {
	
	 @Autowired
	    private CartItemRepository cartRepo;

	    @Autowired
	    private ProductRepository productRepo;

	    @Autowired
	    private UserRepository userRepo;

	    // ✅ ADD TO CART
	    @Override
	    public CartResponseDTO addToCart(CartRequestDTO dto) {

	        // 🔴 1. VALIDATION
	        if (dto.getProductId() == null || dto.getUserId() == null) {
	            throw new RuntimeException("ProductId or UserId is null");
	        }

	        // 🔴 2. FETCH PRODUCT & USER
	        Product product = productRepo.findById(dto.getProductId())
	                .orElseThrow(() -> new RuntimeException("Product not found"));

	        User user = userRepo.findById(dto.getUserId())
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        // 🔴 3. CHECK IF ITEM ALREADY EXISTS IN CART
	        Optional<CartItem> existingItem =
	                cartRepo.findByUserIdAndProductId(user.getId(), product.getId());

	        CartItem cartItem;

	        if (existingItem.isPresent()) {
	            // ✅ UPDATE QUANTITY (REAL LOGIC)
	            cartItem = existingItem.get();

	            cartItem.setQuantity(cartItem.getQuantity() + dto.getQuantity());
	        } else {
	            // ✅ CREATE NEW CART ITEM
	            cartItem = new CartItem();
	            cartItem.setUser(user);
	            cartItem.setProduct(product);
	            cartItem.setQuantity(dto.getQuantity());
	        }

	        // 🔴 4. SAVE
	        CartItem saved = cartRepo.save(cartItem);

	        // 🔴 5. RETURN RESPONSE DTO
	        return CartItemMapper.toDTO(saved);
	    }

    @Override
    public void decreaseQuantity(Long cartItemId) {

        CartItem item = cartRepo.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
            cartRepo.save(item);
        } else {
            cartRepo.delete(item); // remove if 1
        }
    }

	    // ✅ GET CART BY USER
	    @Override
	    public List<CartResponseDTO> getCartByUser(Long userId) {

	        List<CartItem> items = cartRepo.findByUserId(userId);

	        return items.stream()
	                .map(CartItemMapper::toDTO)
	                .toList();
	    }

	    // ✅ REMOVE ITEM
	    @Override
	    public void removeItem(Long cartItemId) {

	        if (!cartRepo.existsById(cartItemId)) {
	            throw new RuntimeException("Cart item not found");
	        }

	        cartRepo.deleteById(cartItemId);
	    }

    @Override
    public void clearCart(Long userId) {
        List<CartItem> items = cartRepo.findByUserId(userId);
        cartRepo.deleteAll(items);
    }

}
