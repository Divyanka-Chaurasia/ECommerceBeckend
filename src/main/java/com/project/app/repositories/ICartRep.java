package com.project.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.app.entity.Cart;
import com.project.app.entity.User;
public interface ICartRep extends JpaRepository<Cart, String> {

	Optional<Cart> findByUser(User user);
	
	
}
