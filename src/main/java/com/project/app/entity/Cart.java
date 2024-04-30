package com.project.app.entity;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cart {

	@Id
	private String cartId;
	private Date createdAt;
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "cart")
	@JsonIgnore
	List<CartItem> cartItem;
	
	
	@OneToOne
	private User user;
}
