package com.project.app.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.app.entity.Category;

public interface ICategoryRepo extends JpaRepository<Category, String> {

	
}
