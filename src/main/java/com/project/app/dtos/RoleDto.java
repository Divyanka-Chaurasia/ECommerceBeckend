package com.project.app.dtos;

import com.project.app.entity.Cart;
import com.project.app.entity.Product;

import jakarta.persistence.Entity;
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
public class RoleDto {
	private String roleId;
	private String roleName;
	
}
