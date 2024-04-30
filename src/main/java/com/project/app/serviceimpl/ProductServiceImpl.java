package com.project.app.serviceimpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.project.app.dtos.PageableResponse;
import com.project.app.dtos.ProductDto;
import com.project.app.entity.Category;
import com.project.app.entity.Product;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.helper.Helper;
import com.project.app.iservices.IProductService;
import com.project.app.repositories.ICartRep;
import com.project.app.repositories.ICategoryRepo;
import com.project.app.repositories.IProductRepo;
@Service
public class ProductServiceImpl implements IProductService {

	@Value("${user.profile.image.path}")
	private String imagepath;
	
	@Autowired
	private IProductRepo prodRepo;
	
	@Autowired
	private ICategoryRepo catRepo;
	
	@Autowired
	private ModelMapper mapper;
	@Override
	public ProductDto createProdDto(ProductDto productDto) {
		String productId = UUID.randomUUID().toString();
		productDto.setProductId(productId);
		Product product = mapper.map(productDto, Product.class);
		product.setProductImageName(productDto.getProductImageName());
		prodRepo.save(product);
		return mapper.map(product, ProductDto.class);
	}
	@Override
	public ProductDto upadteProduct(ProductDto productDto, String productId) {
		Product product = prodRepo.findById(productId).orElseThrow(
				()-> new ResourceNotFoundException("product not found"));
		product.setTitle(productDto.getTitle());
		product.setStock(true);
		product.setQuantity(productDto.getQuantity());
		product.setPrice(productDto.getPrice());
		product.setDescriptions(productDto.getDescription());
		product.setDiscountedPrice(productDto.getDiscountedPrice());
		product.setProductImageName(productDto.getProductImageName());
		product.setLive(false);
		product.setProductImageName(productDto.getProductImageName());
		prodRepo.save(product);
		return mapper.map(product, ProductDto.class);
	}
	@Override
	public void deleteProduct(String productId) {
		Product product = prodRepo.findById(productId).orElseThrow(
				()-> new ResourceNotFoundException("category not found"));
		String fullPath = imagepath+product.getProductImageName();
		Path path = Paths.get(fullPath);
		try {
			Files.delete(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		prodRepo.delete(product);

	}
	@Override
	public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase("desc")?
				(Sort.by(sortBy).descending()):
				(Sort.by(sortBy).ascending());
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		Page<Product> page = prodRepo.findAll(pageable);
		PageableResponse<ProductDto> response = Helper.getPageableResponse(page, ProductDto.class);
			return response;
	}
	@Override
	public ProductDto get(String productId) {
		Product product = prodRepo.findById(productId).orElseThrow(
				()-> new ResourceNotFoundException("product not found"));
		return mapper.map(product, ProductDto.class);
	}
	@Override
	public PageableResponse<ProductDto> getAllLiveProduct(int pageNumber, int pageSize, String sortBy,
			String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase("desc")?
				(Sort.by(sortBy).descending()):
				(Sort.by(sortBy).ascending());
		 
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		Page<Product> listProduct =  prodRepo.findByLiveIsTrue(pageable);
		if(listProduct==null)
		{
			throw new ResourceNotFoundException("products are not present");
		}
		
		PageableResponse<ProductDto> response = Helper.getPageableResponse(listProduct, ProductDto.class);
		
			return response;
	}
	@Override
	public PageableResponse<ProductDto> searchBytitle(int pageNumber, int pageSize, String sortBy,
			String sortDirection,String title) {
		Sort sort = sortDirection.equalsIgnoreCase("desc")?
				(Sort.by(sortBy).descending()):
				(Sort.by(sortBy).ascending());
		 
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		Page<Product> listProduct =  prodRepo.findByCustomQuery(title,pageable);
		if(listProduct==null)
		{
			throw new ResourceNotFoundException("products are not present");
		}
		
		PageableResponse<ProductDto> response = Helper.getPageableResponse(listProduct, ProductDto.class);
		
			return response;
	}
	@Override
	public ProductDto giveCategoryToProduct(String productId, String categoryId) {
		Product product = prodRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("product not found"));
		Category catagory = catRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found"));
		Category isCategoryExist = product.getCategory();
		if(isCategoryExist==null && catagory!=null)
		{
			product.setCategory(catagory);
			prodRepo.save(product);
			ProductDto newProductDto = mapper.map(product, ProductDto.class);
			return newProductDto;
		}
		else 
			throw new ResourceNotFoundException("already this product contains category");
	}
	@Override
	public ProductDto updateProductCategory(String productId,String categoryId) {
		Product product = prodRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("productsss not found"));
		Category category = catRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found"));
		Category newCategory = new Category();
		newCategory.setCatagoryId(categoryId);
		product.setCategory(newCategory);
		prodRepo.save(product);
		ProductDto prodDto = mapper.map(product, ProductDto.class); 
		return prodDto;
	}
	@Override
	public PageableResponse<ProductDto> getAllProductOfCategory(String categoryId, int pageNumber, int pageSize,
			String sortBy, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase("desc")?
				(Sort.by(sortBy).descending()):
				(Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		Category category = catRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
		Page<Product> listProduct =  prodRepo.getAllProductByCategory(category,pageable);	
		if(listProduct==null)
		{
			throw new ResourceNotFoundException("products are not present");
		}		PageableResponse<ProductDto> response = Helper.getPageableResponse(listProduct, ProductDto.class);		
			return response;
	}
}