package com.project.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.app.dtos.OrderItemDto;
import com.project.app.iservices.IOrderItemService;

@RestController
@RequestMapping("/orderItem")
public class OrderItemController {

	@Autowired
	private IOrderItemService orderItemService;
	
	@PostMapping
	public ResponseEntity<OrderItemDto>  createOrderItem(@RequestBody OrderItemDto orderItemDto)
	{
		return new ResponseEntity<>(orderItemService.createOrderItem(orderItemDto),HttpStatus.CREATED);
	}
}
