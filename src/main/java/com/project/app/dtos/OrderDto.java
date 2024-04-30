package com.project.app.dtos;
import java.util.Date;
import java.util.List;
import com.project.app.entity.OrderItem;
import com.project.app.entity.User;
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
public class OrderDto {
	private String orderId;
	private String orderStatus;
	private Double orderAmount;
	private String paymentStatus;	
	private String billingAddress;
	private String billingPhone;
	private String billingName;
	private Date orderDate;
	private Date deliverDate;
    private User user;
	private List<OrderItem> orderItem;
}
