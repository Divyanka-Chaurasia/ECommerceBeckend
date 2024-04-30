package com.project.app.serviceimpl;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.project.app.dtos.CategoryDto;
import com.project.app.dtos.PageableResponse;
import com.project.app.entity.Category;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.helper.Helper;
import com.project.app.iservices.ICategoryService;
import com.project.app.repositories.ICategoryRepo;
@Service
public class CategoryServiceImpl implements ICategoryService {

	@Autowired
	private ICategoryRepo repo;
	@Autowired
	private ModelMapper mapper;
	
	@Value("${user.profile.image.path}")
	private String imagepath;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		String categoryId = UUID.randomUUID().toString();
		categoryDto.setCatagoryId(categoryId);
		Category category = mapper.map(categoryDto, Category.class);
		repo.save(category);
		return mapper.map(category, CategoryDto.class);
	}
	@Override
	public CategoryDto upadteCategory(CategoryDto categoryDto, String categoryId) {
		Category category = repo.findById(categoryId).orElseThrow(
				()-> new ResourceNotFoundException("category not found"));
		category.setTitle(categoryDto.getTitle());
		category.setDescription(categoryDto.getDescription());
		category.setCoverImage(categoryDto.getCoverImage());
		repo.save(category);
		return mapper.map(category, CategoryDto.class);
	}
	@Override
	public void deleteCategory(String categoryId) {
		Category category = repo.findById(categoryId).orElseThrow(
				()-> new ResourceNotFoundException("category not found"));
		String fullPath = imagepath+category.getCoverImage();
		Path path = Paths.get(fullPath);
		try {
			Files.delete(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        repo.delete(category);
	}
	@Override
	public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDirection) {
	Sort sort = sortDirection.equalsIgnoreCase("desc")?
			(Sort.by(sortBy).descending()):
			(Sort.by(sortBy).ascending());
	
	Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
	Page<Category> page = repo.findAll(pageable);
	PageableResponse<CategoryDto> response = Helper.getPageableResponse(page, CategoryDto.class);
		return response;
	}
	@Override
	public CategoryDto get(String categoryId) {
		Category category = repo.findById(categoryId).orElseThrow(
				()-> new ResourceNotFoundException("category not found"));
		return mapper.map(category, CategoryDto.class);
	}
}