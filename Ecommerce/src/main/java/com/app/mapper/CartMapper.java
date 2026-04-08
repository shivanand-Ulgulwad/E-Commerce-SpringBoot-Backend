package com.app.mapper;

import com.app.dto.CartResponseDTO;
import com.app.entity.CartItem;

public class CartMapper {

    public static CartResponseDTO toDTO(CartItem item) {

        CartResponseDTO dto = new CartResponseDTO();

        dto.setId(item.getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getProduct().getPrice());
        dto.setTotalPrice(
                item.getProduct().getPrice() * item.getQuantity()
        );

        return dto;
    }
}
