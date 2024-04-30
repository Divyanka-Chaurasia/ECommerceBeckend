package com.project.app.dtos;
import com.project.app.validate.ImageNameValid;
import jakarta.validation.constraints.NotBlank;
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
public class CategoryDto {

	private String catagoryId;
	@NotBlank(message="title is required")
	private String title;
	private String description;
	@ImageNameValid(message="invalid image")
	private String coverImage;
}