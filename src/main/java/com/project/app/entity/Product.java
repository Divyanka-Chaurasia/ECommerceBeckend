package com.project.app.entity;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="products_tabs")
public class Product {

	@Id
	@Column(name="prodId")
    private	String productId; 
    @Column(name="title")
	private	String   title; 
    @Column(name="descriptionse")
    private String descriptions; 
    @Column(name="price")
    private double price;
    @Column(name="disPrice")
    private double discountedPrice;
    @Column(name="quantity")
    private Integer quantity;
    @Column(name="createdDate")
    private Date createdDate;
	private Boolean live=false;
	@Column(name="stock")
	private boolean stock;
	@Column(name="prodImageName")
	private String productImageName ;	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="catagory")
	private Category category;
	
	@OneToMany
	@JoinColumn(name="product")
	@JsonIgnore
	private List<CartItem> cartItem;
}
