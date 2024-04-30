package com.project.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.app.dtos.CartDto;
import com.project.app.iservices.ICartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private ICartService cartService;
	@PostMapping("/{userId}")
	public ResponseEntity<Map<String,Object>> createCart(@PathVariable String userId)
	{
		return new ResponseEntity<>(cartService.createCart(userId),HttpStatus.CREATED);
	}
	
	@GetMapping("/getCart/{userId}")
	public ResponseEntity<CartDto> getCartByUser(@PathVariable String userId)
	{
		return new ResponseEntity<>(cartService.getCartByUser(userId),HttpStatus.OK);
	}
}
