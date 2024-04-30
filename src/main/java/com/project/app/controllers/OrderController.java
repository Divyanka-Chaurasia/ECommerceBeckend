package com.project.app.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.app.dtos.OrderDto;
import com.project.app.iservices.IOrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private IOrderService orderService;
	@PostMapping("/{userId}")
	public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto,@PathVariable String userId)
	{
		return new ResponseEntity<>(orderService.createOrder(orderDto, userId),HttpStatus.CREATED);
	}
}
