package com.project.app.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import com.project.app.entity.Category;
import com.project.app.entity.Product;
public interface IProductRepo extends JpaRepository<Product, String> {

	@Query("SELECT p FROM Product p WHERE p.title LIKE %:keyword%")
    Page<Product> findByCustomQuery(@Param("keyword") String keyword, Pageable pageable);
	
	 Page<Product> findByLiveIsTrue(Pageable pageable);
	 
	 Page<Product> getAllProductByCategory(Category category,Pageable pageable);
	 
	 @Query("SELECT p.price FROM Product p WHERE p.productId = :productId")
	   Double findPriceByProductId(@Param("productId") String productId);

  
}
