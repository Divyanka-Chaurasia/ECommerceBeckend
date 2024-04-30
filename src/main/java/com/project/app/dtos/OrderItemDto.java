package com.project.app.dtos;

import com.project.app.entity.Orders;
import com.project.app.entity.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Builder
public class OrderItemDto {
	private String orderItemId;
	private Integer quantity;
	private Double totalPrice;
	private Product product;
	private Orders orders;
}
