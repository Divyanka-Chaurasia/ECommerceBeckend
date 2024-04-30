package com.project.app.iservices;
import java.util.Map;

import com.project.app.dtos.CartDto;
import com.project.app.dtos.CartItemDto;
import com.project.app.dtos.PageableResponse;
public interface ICartItemServices {

	CartItemDto createCartItem(CartItemDto cartItemDto);
	CartItemDto upadteCartItem(CartItemDto cartItemDto,String CartItemId);
	void deleteCartItem(String cartItemid);
	PageableResponse<CartItemDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDirection);
	CartItemDto get(String cartItemid);
    public CartItemDto removeQuantityOfCartItem(String cartItemId);
    public CartItemDto addQantityOfCartItem(String cartItemId);
    public Map<String,Object> clearCartItem(String cartId);
}
