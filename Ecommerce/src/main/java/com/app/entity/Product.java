package com.app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "Products")
public class Product {
	 @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 	
	 	@NonNull
	    private String name;
	 	
	 	@NonNull
	    private double price;

	    @ManyToOne
        @JoinColumn(name = "category_id")
	    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
}
