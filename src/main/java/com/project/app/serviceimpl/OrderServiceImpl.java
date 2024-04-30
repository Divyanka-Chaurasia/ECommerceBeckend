package com.project.app.serviceimpl;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.app.dtos.CategoryDto;
import com.project.app.dtos.OrderDto;
import com.project.app.dtos.PageableResponse;
import com.project.app.entity.Category;
import com.project.app.entity.Orders;
import com.project.app.entity.User;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.helper.Helper;
import com.project.app.iservices.IOrderService;
import com.project.app.repositories.IOrderRepo;
import com.project.app.repositories.UserRepo;
@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private IOrderRepo orderRepo;	
	@Autowired
	private UserRepo userRepo;	
	@Autowired
	private ModelMapper mapper;
	@Override
	public OrderDto createOrder(OrderDto orderDto,String userId) {
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not found"));
		String orderId = UUID.randomUUID().toString();
		orderDto.setOrderId(orderId);
		orderDto.setUser(new User(userId));
		Orders orders = mapper.map(orderDto, Orders.class);
		orderRepo.save(orders);
		OrderDto neworderDto = mapper.map(orders, OrderDto.class);
		return neworderDto;
	}

	@Override
	public OrderDto getOrderByUser(String userId,String orderId) {
//		Orders order = orderRepo.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("order not found"));
//		Optional<Orders> orders = orderRepo.existsByUserId(userId);
//		if(orders.isPresent())
//		{
//			OrderDto orderDto = mapper.map(orders, OrderDto.class);
//			return orderDto;
//		}
//		else 
//		 throw new ResourceNotFoundException("this user is not placed any order");
		return null;
	}

	@Override
	public PageableResponse<OrderDto> getAllOrder(int pageNumber, int pageSize, String sortBy, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase("desc")?
				(Sort.by(sortBy).descending()):
				(Sort.by(sortBy).ascending());
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		Page<Orders> page = orderRepo.findAll(pageable);
		PageableResponse<OrderDto> response = Helper.getPageableResponse(page, OrderDto.class);
			return response;
	}

	@Override
	public OrderDto updateOrder(OrderDto orderDto, String orderId) {
		
		return null;
	}

	@Override
	public void removeOrder(String orderId, String userId) {
				
	}
}