package com.project.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.app.entity.User;

public interface UserRepo extends JpaRepository<User, String> {

	List<User> findByNameContaining(String keyword);

	boolean existsByEmail(String email);

	  public Optional<User> findByEmailAndPassword(String email,String userPassword);
	  
    User findByName(String name);
}
