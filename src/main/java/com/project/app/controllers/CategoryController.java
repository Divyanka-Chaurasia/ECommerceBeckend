package com.project.app.controllers;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.dtos.ApiResponseMessage;
import com.project.app.dtos.CategoryDto;
import com.project.app.dtos.ImageResponse;
import com.project.app.dtos.PageableResponse;
import com.project.app.dtos.ProductDto;
import com.project.app.entity.Category;
import com.project.app.iservices.ICategoryService;
import com.project.app.iservices.IFileService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
@CrossOrigin
public class CategoryController {

	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private IFileService fileService;	
	
	private CategoryDto readValue;
	
	@Value("${user.profile.image.path}")
	private String imageUploadpath;
	
	@PostMapping("/create")
	public ResponseEntity<ImageResponse> createCategory(@Validated @RequestPart("imageUpload") MultipartFile image,@RequestPart String category) throws IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		readValue = mapper.readValue(category, CategoryDto.class);
		String imageName = fileService.uploadFile(image,imageUploadpath);
		readValue.setCoverImage(imageName);
		CategoryDto categoryDto = categoryService.createCategory(readValue);
		ImageResponse imageResponse = ImageResponse.builder()
				.imageName(imageName)
				.message("image is uploaded successfully")
				.success(true)
				.status(HttpStatus.CREATED)
				.build();
		return new ResponseEntity<ImageResponse>(imageResponse,HttpStatus.CREATED);
	}	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String id)
	{
		categoryService.deleteCategory(id);
		ApiResponseMessage response=ApiResponseMessage.builder() 
				.msg("Category is deleted")
				.success(true)
				.status(HttpStatus.OK) 
				.build();
		return new ResponseEntity<ApiResponseMessage>(response,HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
	        @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
	        @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
	        @RequestParam(value = "sortDirection",defaultValue = "asc",required = false) String sortDirection         
			)
	{		
		return new ResponseEntity<>(categoryService.getAll(pageNumber, pageSize, sortBy, sortDirection),HttpStatus.OK);		
	}	
	@GetMapping("/get/{id}")
	public ResponseEntity<CategoryDto> getOne(@PathVariable String id)
	{
		return new ResponseEntity<CategoryDto>(categoryService.get(id),HttpStatus.OK);
	}
	@PutMapping("/update/{id}")
	public ResponseEntity<ImageResponse> updateUser(@Valid @RequestPart String category,@RequestPart("updateImage") MultipartFile image,@PathVariable("id") String id) throws IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		CategoryDto categoryDto = mapper.readValue(category, CategoryDto.class);
		String imageName = fileService.uploadFile(image,imageUploadpath);
		categoryDto.setCoverImage(imageName);
		CategoryDto updatedCategoryDto = categoryService.upadteCategory(categoryDto, id);
		ImageResponse imageResponse = ImageResponse.builder()
				.imageName(imageName)
				.message("image is updated successfully")
				.success(true)
				.status(HttpStatus.CREATED)
				.build();
		return new ResponseEntity<ImageResponse>(imageResponse,HttpStatus.CREATED);
	}
	@GetMapping("/image/{categoryId}")
	public void serveUserImage(@PathVariable String categoryId,HttpServletResponse response) throws IOException
	{
		CategoryDto categoryDto = categoryService.get(categoryId);
		InputStream resource = fileService.getResource(imageUploadpath, categoryDto.getCoverImage());
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	@GetMapping("/image/{catId}")
	public void serveCatImage(@PathVariable String catId,HttpServletResponse response) throws IOException
	{
		CategoryDto categoryDto = categoryService.get(catId);
		InputStream resource = fileService.getResource(imageUploadpath, categoryDto.getCoverImage());
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}