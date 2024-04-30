package com.project.app.serviceimpl;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.project.app.entity.Product;
import com.project.app.dtos.CartItemDto;
import com.project.app.dtos.PageableResponse;
import com.project.app.entity.Cart;
import com.project.app.entity.CartItem;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.helper.Helper;
import com.project.app.iservices.ICartItemServices;
import com.project.app.repositories.ICartItemRepo;
import com.project.app.repositories.ICartRep;
import com.project.app.repositories.IProductRepo;

@Service
public class CartItemServiceImpl implements ICartItemServices {

	@Autowired
	private ICartItemRepo cartItemRepo;
	@Autowired
	private IProductRepo productRepo;
	@Autowired
	private ICartRep cartRepo;
	@Autowired
	private ModelMapper mapper;

	@Override
	public CartItemDto createCartItem(CartItemDto cartItemDto) {
	    String cartId = cartItemDto.getCart().getCartId();
	    Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

	    String productId = cartItemDto.getProduct().getProductId();
	    Product product = productRepo.findById(productId)
	            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

	    if (cartItemRepo.existsByCartAndProduct(cart, product)) {
	        CartItem cartItem = cartItemRepo.findByCartAndProduct(cart, product);
	        if (cartItem.getQuantity() == null) {
	            cartItem.setQuantity(1);

	        } else {
	            Integer quantity = cartItem.getQuantity() + 1;
	            cartItem.setQuantity(quantity);
	        }
	 
	        cartItem.setPrice(product.getPrice() * cartItem.getQuantity());
	        cartItemRepo.save(cartItem);
	        CartItemDto newCartItemDto = mapper.map(cartItem, CartItemDto.class);
	        return newCartItemDto;

	    }

	    String cartItemId = UUID.randomUUID().toString();
	    cartItemDto.setCartItemId(cartItemId);
	    CartItem cartItem = mapper.map(cartItemDto, CartItem.class);
	    cartItem.setPrice(product.getPrice() * cartItemDto.getQuantity());
	    cartItemRepo.save(cartItem);

	    return mapper.map(cartItem, CartItemDto.class);
	}

	@Override
	public CartItemDto upadteCartItem(CartItemDto cartItemDto, String cartItemId) {
		CartItem cartItem = cartItemRepo.findById(cartItemId)
				.orElseThrow(() -> new ResourceNotFoundException("cart Item not found"));
		Product product = productRepo.findById(cartItemDto.getProduct().getProductId())
				.orElseThrow(() -> new ResourceNotFoundException("product not found"));
		Double price = product.getPrice();
		cartItem.setQuantity(cartItemDto.getQuantity());
		cartItem.setPrice(product.getPrice()*cartItem.getQuantity());
		cartItemRepo.save(cartItem);
		CartItemDto newCartItemDto = mapper.map(cartItem, CartItemDto.class);
		return newCartItemDto;
	}

	@Override
	public void deleteCartItem(String cartItemid) {

		CartItem cartItem = cartItemRepo.findById(cartItemid)
				.orElseThrow(() -> new ResourceNotFoundException("cart Item not found"));
		cartItemRepo.deleteById(cartItemid);
	}

	@Override
	public PageableResponse<CartItemDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase("desc") ? (Sort.by(sortBy).descending())
				: (Sort.by(sortBy).ascending());

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<CartItem> page = cartItemRepo.findAll(pageable);
		PageableResponse<CartItemDto> response = Helper.getPageableResponse(page, CartItemDto.class);
		return response;
	}

	@Override
	public CartItemDto get(String cartItemid) {
		CartItem cartItem = cartItemRepo.findById(cartItemid)
				.orElseThrow(() -> new ResourceNotFoundException("cart item not found"));
		CartItemDto cartItemDto = mapper.map(cartItem, CartItemDto.class);
		return cartItemDto;
	}
	@Override
	public CartItemDto removeQuantityOfCartItem(String cartItemId) {
	    CartItem cartItem = cartItemRepo.findById(cartItemId)
	            .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

	    if (cartItem.getQuantity() > 0) {
	        Integer newQuantity = cartItem.getQuantity() - 1;
	        cartItem.setQuantity(newQuantity);

	       
	        if (newQuantity > 0) {
	            cartItem.setPrice(cartItem.getPrice() * newQuantity);
	        } else {
	         
	            cartItem.setPrice(0.0);
	        }

	        cartItemRepo.save(cartItem);
	    } else {
	        cartItemRepo.delete(cartItem);
	    }

	    CartItemDto cartItemDto = mapper.map(cartItem, CartItemDto.class);

	    return cartItemDto;
	}
	@Override
	public CartItemDto addQantityOfCartItem(String cartItemId) {
		CartItem cartItem = cartItemRepo.findById(cartItemId)
				.orElseThrow(() -> new ResourceNotFoundException("cart item not found"));
		Integer qauntity = cartItem.getQuantity() + 1;
		cartItem.setQuantity(qauntity);
		cartItem.setPrice(cartItem.getPrice()*cartItem.getQuantity());
		cartItemRepo.save(cartItem);
		CartItemDto cartItemDto = mapper.map(cartItem, CartItemDto.class);
		return cartItemDto;
	}

	@Override
	public Map<String, Object> clearCartItem(String cartId) {
		Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("cart not found"));

		for (CartItem cartItem : cart.getCartItem()) {
			cartItemRepo.delete(cartItem);
		}
		Map<String, Object> response = new HashMap<>();
		response.put("message", "Cart items cleared successfully");
		return response;
	}
}