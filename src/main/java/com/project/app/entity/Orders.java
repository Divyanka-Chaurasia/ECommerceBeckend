package com.project.app.entity;
import java.util.Date;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="orders")
public class Orders {
	@Id
	private String orderId;
	private String orderStatus;
	private Double orderAmount;
	private String paymentStatus;	
	private String billingAddress;
	private String billingPhone;
	private String billingName;
	private Date orderDate;
	private Date deliverDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user")
    private User user;
	
	@OneToMany(mappedBy = "orders",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private List<OrderItem> orderItem;
}
