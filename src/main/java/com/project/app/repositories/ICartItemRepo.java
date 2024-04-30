package com.project.app.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.app.entity.CartItem;
import com.project.app.entity.Product;
import com.project.app.entity.Cart;
public interface ICartItemRepo extends JpaRepository<CartItem, String> {

    boolean existsByCartAndProduct(Cart cart, Product product);

	CartItem findByCartAndProduct(Cart cart, Product product);
}
