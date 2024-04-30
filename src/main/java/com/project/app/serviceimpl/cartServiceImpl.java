package com.project.app.serviceimpl;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.app.dtos.CartDto;
import com.project.app.entity.Cart;
import com.project.app.entity.CartItem;
import com.project.app.entity.Product;
import com.project.app.entity.User;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.iservices.ICartService;
import com.project.app.repositories.ICartItemRepo;
import com.project.app.repositories.ICartRep;
import com.project.app.repositories.IProductRepo;
import com.project.app.repositories.UserRepo;
@Service
public class cartServiceImpl implements ICartService {

	@Autowired
	private ICartRep cartRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
    private	ICartItemRepo cartItemRepo;	
	@Autowired
	private IProductRepo productRepo;
	@Autowired
	private ModelMapper mapper;
		

	@Override
	public CartDto getCartByUser(String userId) {
	    User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
	    Optional<Cart> cart = cartRepo.findByUser(user);
	    
	    if (cart.isPresent()) {
	        CartDto cartDto = mapper.map(cart.get(), CartDto.class); // Use cart.get() to retrieve the Cart object
	        return cartDto;
	    } else {
	        throw new ResourceNotFoundException("Cart not found with this user id");
	    }
	}
	@Override
	public Map<String,Object> createCart(String userId) {
		Map<String,Object> map = new HashMap<String,Object>();
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not found"));
		Optional<Cart> cart = cartRepo.findByUser(user);
		if(cart.isPresent())
		{
			throw new ResourceNotFoundException("cart present with this user");
		}
		else {
		String cartId = UUID.randomUUID().toString(); 
		Cart newCart = new Cart();
		newCart.setCartId(cartId);	
		newCart.setUser(new User(userId));
		cartRepo.save(newCart);	
		map.put("newCart",newCart);
		return map;
		}
	}


}
