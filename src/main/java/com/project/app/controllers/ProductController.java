package com.project.app.controllers;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.dtos.ApiResponseMessage;
import com.project.app.dtos.ImageResponse;
import com.project.app.dtos.PageableResponse;
import com.project.app.dtos.ProductDto;
import com.project.app.dtos.UserDto;
import com.project.app.iservices.IFileService;
import com.project.app.iservices.IProductService;

import jakarta.servlet.http.HttpServletResponse;
@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductController {

	@Autowired
	private IProductService prodService;
	
	private ProductDto readValue;
	@Autowired
	private IFileService fileService;	
	
	@Value("${user.profile.image.path}")
	private String imageUploadpath;
	
	@PostMapping
	public ResponseEntity<ImageResponse> createProduct(@Validated @RequestPart("imageUpload") MultipartFile image,@RequestPart String product) throws IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		readValue = mapper.readValue(product, ProductDto.class);
		String imageName = fileService.uploadFile(image,imageUploadpath);
		readValue.setProductImageName(imageName);
		ProductDto productDto = prodService.createProdDto(readValue);
		ImageResponse imageResponse = ImageResponse.builder()
				.imageName(imageName)
				.message("image is uploaded successfully")
				.success(true)
				.status(HttpStatus.CREATED)
				.build();
		return new ResponseEntity<ImageResponse>(imageResponse,HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{productId}")
	public ResponseEntity<ProductDto> updateProduct(@PathVariable String productId , @RequestBody ProductDto productDto)
	{
	    return new ResponseEntity<>(prodService.upadteProduct(productDto, productId),HttpStatus.OK);	
  	}
	
	@GetMapping("/getallProductByTitle/{title}")
	public ResponseEntity<PageableResponse<ProductDto>> getAllUsers(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "3",required = false) int pageSize,
			@RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
			@RequestParam(value = "sortDirection",defaultValue = "asc",required = false) String sortDirection, 
			@PathVariable String title
			){
		
		return new ResponseEntity<>(prodService.searchBytitle(pageNumber,pageSize,sortBy,sortDirection,title),HttpStatus.OK);
	}
	
	@GetMapping("/getLiveProduct")
	public ResponseEntity<PageableResponse<ProductDto>> getAllLiveProduct(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "3",required = false) int pageSize,
			@RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
			@RequestParam(value = "sortDirection",defaultValue = "asc",required = false) String sortDirection
			)
	{
		return new ResponseEntity<>(prodService.getAllLiveProduct(pageNumber, pageSize, sortBy, sortDirection),HttpStatus.OK);
	}
	
	@PutMapping("/updateProductCategory/{productId}/{categoryId}")
	public ResponseEntity<ProductDto> updateProductCategory(@PathVariable String categoryId,@PathVariable String productId)
	{
		return new ResponseEntity<>(prodService.updateProductCategory(productId,categoryId),HttpStatus.OK);
	}
	
	@GetMapping("/getAllProductByCategory/{categoryId}")
	public ResponseEntity<PageableResponse<ProductDto>> getAllProductByCategory(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "3",required = false) int pageSize,
			@RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
			@RequestParam(value = "sortDirection",defaultValue = "asc",required = false) String sortDirection,
			@PathVariable String categoryId
			)
	{
	   return new ResponseEntity<>(prodService.getAllProductOfCategory(categoryId, pageNumber, pageSize, sortBy, sortDirection),HttpStatus.OK);	
	}
	
	
	
	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId)
	{
		prodService.deleteProduct(productId);
		ApiResponseMessage response=ApiResponseMessage.builder() 
				.msg("product is deleted")
				.success(true)
				.status(HttpStatus.OK) 
				.build();
		return new ResponseEntity<ApiResponseMessage>(response,HttpStatus.OK);
	}
	
	@GetMapping("/getAllProduct")
	public ResponseEntity<PageableResponse<ProductDto>> getAllProduct(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "3",required = false) int pageSize,
			@RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
			@RequestParam(value = "sortDirection",defaultValue = "asc",required = false) String sortDirection
			)
	{
		System.err.println(pageSize);
		return new ResponseEntity<>(prodService.getAll(pageNumber, pageSize, sortBy, sortDirection),HttpStatus.OK);
	}
	
	@GetMapping("/image/{prodId}")
	public void serveProdImage(@PathVariable String prodId,HttpServletResponse response) throws IOException
	{
		System.err.println(prodId);
		ProductDto product = prodService.get(prodId);
		InputStream resource = fileService.getResource(imageUploadpath, product.getProductImageName());
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	@GetMapping("/product/{productId}")
	public ResponseEntity<ProductDto> getProduct(@PathVariable String productId)
	{
		return new ResponseEntity<>(prodService.get(productId),HttpStatus.OK);
	}
	
	@PutMapping("giveCategory/{prodId}/{catId}")
	public ResponseEntity<ProductDto> giveCategoryToProduct(@PathVariable String prodId,@PathVariable String catId )
	{
		return new ResponseEntity<>(prodService.giveCategoryToProduct(prodId, catId),HttpStatus.OK);
	}

}
