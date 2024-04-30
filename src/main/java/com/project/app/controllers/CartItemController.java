package com.project.app.controllers;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.project.app.dtos.ApiResponseMessage;
import com.project.app.dtos.CartItemDto;
import com.project.app.dtos.PageableResponse;
import com.project.app.iservices.ICartItemServices;
@RestController
@RequestMapping("/cartItem")
public class CartItemController {
	
	@Autowired
	private ICartItemServices cartItemService;
	
	@PostMapping("/create")
	public ResponseEntity<CartItemDto> createCartItemDto(@RequestBody CartItemDto cartItemDto)
	{
		
		return new ResponseEntity<>(cartItemService.createCartItem(cartItemDto),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{cartItemId}")
	public ResponseEntity<ApiResponseMessage> deleteCartItemFromCart(@PathVariable String cartItemId)
	{
	    cartItemService.deleteCartItem(cartItemId);
	    ApiResponseMessage response=ApiResponseMessage.builder() 
				.msg("cartItem is deleted")
				.success(true)
				.status(HttpStatus.OK) 
				.build();
		return new ResponseEntity<ApiResponseMessage>(response,HttpStatus.OK);
    }

	@GetMapping("/get/{cartItemId}")
	public ResponseEntity<CartItemDto> getCartItem(@PathVariable String cartItemId)
	{
		return new ResponseEntity<>(cartItemService.get(cartItemId),HttpStatus.OK);
	}
	
	@GetMapping("/getAllCartItem")
	public ResponseEntity<PageableResponse<CartItemDto>> getAllCartItemDto(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
	        @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
	        @RequestParam(value = "sortBy",defaultValue = "price",required = false) String sortBy,
	        @RequestParam(value = "sortDirection",defaultValue = "asc",required = false) String sortDirection         
			)
	{
		return new ResponseEntity<>(cartItemService.getAll(pageNumber, pageSize, sortBy, sortDirection),HttpStatus.OK);
	}
	
	@PutMapping("/removeCartItemQuantity/{cartItemId}")
	public ResponseEntity<ApiResponseMessage> removeCartItemQuantity(@PathVariable String cartItemId)
	{
		CartItemDto cartItemDto = cartItemService.removeQuantityOfCartItem(cartItemId);
		Integer quantity = cartItemDto.getQuantity();
		ApiResponseMessage response=ApiResponseMessage.builder() 
				.msg(" 1 quantity "+quantity+" of product is  removed from cart")
				.success(true)
				.status(HttpStatus.OK) 
				.build();
		return new ResponseEntity<ApiResponseMessage>(response,HttpStatus.OK);
	}
	
	@PostMapping("/increaseQantityOfCartItem/{cartItemId}")
	public ResponseEntity<CartItemDto> increaseQantityOfCartItem(@PathVariable String cartItemId)
	{
		return new ResponseEntity<>(cartItemService.addQantityOfCartItem(cartItemId),HttpStatus.OK);
	}
	
	@PutMapping("/updateCartItem/{cartItemId}")
	public ResponseEntity<CartItemDto> updateCartItem(@PathVariable String cartItemId,@RequestBody CartItemDto cartItemDto)
	{
		return new ResponseEntity<>(cartItemService.upadteCartItem(cartItemDto,cartItemId),HttpStatus.OK);
	}
	
	@DeleteMapping("/clearCartItem/{cartId}")
	public ResponseEntity<Map<String,Object>> clearCartItem(@PathVariable String cartId)
	{
		return new ResponseEntity<>(cartItemService.clearCartItem(cartId),HttpStatus.OK);
	}
}