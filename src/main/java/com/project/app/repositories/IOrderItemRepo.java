package com.project.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.app.entity.OrderItem;

public interface IOrderItemRepo extends JpaRepository<OrderItem, String> {

}
