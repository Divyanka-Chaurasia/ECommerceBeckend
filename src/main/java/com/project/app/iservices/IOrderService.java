package com.project.app.iservices;
import com.project.app.dtos.OrderDto;
import com.project.app.dtos.PageableResponse;
public interface IOrderService {
	//create
	OrderDto createOrder(OrderDto orderDto,String userId);
	//getOrderByUser
   OrderDto getOrderByUser(String userId,String orderId);
	//getAllOrder
   PageableResponse<OrderDto> getAllOrder(int pageNumber, int pageSize, String sortBy, String sortDirection);
	//updateOrder
   OrderDto updateOrder(OrderDto orderDto,String orderId);
	//remove
   void removeOrder(String orderId,String userId);
}
