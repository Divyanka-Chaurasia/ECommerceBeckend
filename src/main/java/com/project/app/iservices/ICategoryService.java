package com.project.app.iservices;
import com.project.app.dtos.CategoryDto;
import com.project.app.dtos.PageableResponse;
import com.project.app.dtos.UserDto;
public interface ICategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);
	CategoryDto upadteCategory(CategoryDto categoryDto,String categoryId);
	void deleteCategory(String categoryId);
	PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDirection);
	CategoryDto get(String categoryId);
}
