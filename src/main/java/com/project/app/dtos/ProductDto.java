package com.project.app.dtos;
import java.util.Date;

import com.project.app.entity.Category;
import com.project.app.validate.ImageNameValid;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
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
public class ProductDto {

    private	String productId; 
    @Column(length = 20,nullable = false)
    private	String   title  ; 
    @Column(length = 800)
    private String description; 
    @Column(nullable = false)
    private double price  ;
    private double discountedPrice;
    private Integer quantity; 
    private Date addedDate ;
	private boolean live ;
	private boolean stock; 
	@ImageNameValid(message = "inavalid image")
	private String productImageName ;
	private Category category;
}
