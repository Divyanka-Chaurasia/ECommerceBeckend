package com.project.app.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.app.dtos.OrderDto;
import com.project.app.entity.Orders;
public interface IOrderRepo extends JpaRepository<Orders, String> {

     // Optional<Orders> existsByUserId(String userId);
}
