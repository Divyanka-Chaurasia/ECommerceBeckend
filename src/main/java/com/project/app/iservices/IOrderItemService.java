package com.project.app.iservices;

import com.project.app.dtos.OrderItemDto;
import com.project.app.dtos.OrderDto;


public interface IOrderItemService {

	OrderItemDto createOrderItem(OrderItemDto orderItemDto);
}
