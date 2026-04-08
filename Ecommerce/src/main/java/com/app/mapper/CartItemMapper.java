package com.app.mapper;

import com.app.dto.CartResponseDTO;
import com.app.entity.CartItem;

public class CartItemMapper {

    public static CartResponseDTO toDTO(CartItem item) {

        CartResponseDTO dto = new CartResponseDTO();
        dto.setId(item.getId());
        dto.setQuantity(item.getQuantity());

        // PRODUCT
        if (item.getProduct() != null) {
            dto.setProductId(item.getProduct().getId());
            dto.setProductName(item.getProduct().getName());
            dto.setPrice(item.getProduct().getPrice());
        }

        // USER
        if (item.getUser() != null) {
            dto.setUserId(item.getUser().getId());
        }

        return dto;
    }
}