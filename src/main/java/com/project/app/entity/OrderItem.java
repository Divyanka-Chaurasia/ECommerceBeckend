package com.project.app.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="orders_Items")
public class OrderItem {

	@Id
	private String orderItemId;
	private Integer quantity;
	private Double totalPrice;
	
	@OneToOne
	@JoinColumn(name="product")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name="orders")
	private Orders orders;
}
