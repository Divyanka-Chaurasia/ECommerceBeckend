package com.project.app.dtos;
import java.util.Date;
import java.util.List;
import com.project.app.entity.CartItem;
import com.project.app.entity.User;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {
	@Id
	private String cartId;
	private Date createdAt;
	List<CartItem> cartItem;
	private User user;
}
