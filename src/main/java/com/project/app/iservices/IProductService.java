package com.project.app.iservices;
import com.project.app.dtos.PageableResponse;
import com.project.app.dtos.ProductDto;
public interface IProductService {

	ProductDto createProdDto(ProductDto productDto);
	ProductDto upadteProduct(ProductDto productDto,String productId);
	void deleteProduct(String productId);
	PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDirection);
	ProductDto get(String productId);
	PageableResponse<ProductDto> getAllLiveProduct(int pageNumber, int pageSize, String sortBy, String sortDirection);
	PageableResponse<ProductDto> searchBytitle(int pageNumber, int pageSize, String sortBy, String sortDirection,String title);
	ProductDto giveCategoryToProduct(String productId,String categoryId);  // --> create prouct with category
	ProductDto updateProductCategory(String productId,String categoryId);
	PageableResponse<ProductDto> getAllProductOfCategory(String categoryId,int pageNumber, int pageSize, String sortBy, String sortDirection);
} 
