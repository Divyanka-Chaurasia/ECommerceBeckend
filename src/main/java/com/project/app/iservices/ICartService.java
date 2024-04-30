package com.project.app.iservices;
import java.util.Map;
import com.project.app.dtos.CartDto;
public interface ICartService {

	public Map<String,Object> createCart(String userId);

    public CartDto getCartByUser(String userId);

}
