package com.project.app.serviceimpl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.app.dtos.OrderItemDto;
import com.project.app.dtos.OrderDto;
import com.project.app.entity.OrderItem;
import com.project.app.entity.Orders;
import com.project.app.entity.Product;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.iservices.IOrderItemService;
import com.project.app.repositories.IOrderItemRepo;
import com.project.app.repositories.IOrderRepo;
import com.project.app.repositories.IProductRepo;
@Service
public class OrderItemServiceImpl implements IOrderItemService {

	@Autowired
	private IOrderItemRepo orderItemRepo;
	
	@Autowired
	private IOrderRepo orderRepo;
	
	@Autowired
	private IProductRepo prodRepo;
	@Autowired
	private ModelMapper mapper;

	@Override
	public OrderItemDto createOrderItem(OrderItemDto orderItemDto) {
		Orders orders = orderRepo.findById(orderItemDto.getOrders().getOrderId()).orElseThrow(()-> new ResourceNotFoundException("order not found"));
		Product product = prodRepo.findById(orderItemDto.getProduct().getProductId()).orElseThrow(()-> new ResourceNotFoundException("product not found"));
		String orderItemId = UUID.randomUUID().toString();
		orderItemDto.setOrderItemId(orderItemId);
		orderItemDto.setTotalPrice(orders.getOrderAmount());
		OrderItem orderItem = mapper.map(orderItemDto, OrderItem.class);
		orderItemRepo.save(orderItem);
		OrderItemDto newOrderItemDto = mapper.map(orderItem, OrderItemDto.class);
		return newOrderItemDto;
	}

}