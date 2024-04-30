package com.project.app.controllers;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.project.app.dtos.ApiResponseMessage;
import com.project.app.dtos.ImageResponse;
import com.project.app.dtos.PageableResponse;
import com.project.app.dtos.UserDto;
import com.project.app.iservices.IFileService;
import com.project.app.iservices.IUserServices;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {
	@Autowired
	private IUserServices service; 
	@Autowired
	private IFileService fileService;
	@Value("${user.profile.image.path}")
	private String imageUploadpath;	
	private Logger logger = LoggerFactory.getLogger(UserController.class);	
	/***
	 * create
	 * @param userDto
	 * @return
	 */
	@PostMapping
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
	{
		
         return new ResponseEntity<>(service.createUser(userDto),HttpStatus.CREATED);		
	}
	/***
	 * update user
	 * @param userDto
	 * @param id
	 * @return
	 */
	@PutMapping("/update/{id}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("id") String id)
	{
		UserDto updateUserDto=service.updateUser(userDto, id);
		return ResponseEntity.ok(updateUserDto);
		
		//return new ResponseEntity<>(service.updateUser(userDto, id),HttpStatus.OK);
	}	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String id)
	{
		service.deleteUser(id);
		ApiResponseMessage response=ApiResponseMessage.builder() 
				.msg("User is deleted")
				.success(true)
				.status(HttpStatus.OK) 
				.build();
		return new ResponseEntity<ApiResponseMessage>(response,HttpStatus.OK);
	}
//
//	@GetMapping
//	public ResponseEntity<List<UserDto>> getAll()
//	{
//		return new ResponseEntity<>(service.getAllUsers(),HttpStatus.OK);
//	}
//	
	@GetMapping("/getallpage")
	public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "3",required = false) int pageSize,
			@RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy,
			@RequestParam(value = "sortDirection",defaultValue = "asc",required = false) String sortDirection          
			)
	{	
		return new ResponseEntity<>(service.getAllUsers(pageNumber,pageSize,sortBy,sortDirection),HttpStatus.OK);
	}
	
	@GetMapping("/getOne/{id}")
	public ResponseEntity<UserDto> getOne(@PathVariable("id") String id)
	{
		return new ResponseEntity<>(service.getOneUser(id),HttpStatus.OK);
	}
	
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<UserDto>> searchUsers(@PathVariable String keyword)
	{
		return  ResponseEntity.ok(service.searchUser(keyword));
	}
	
//	@GetMapping("/page/{pageNo}")
//    public ResponseEntity<Page<UserDto>> findPaginated(@PathVariable int pageNo)
//    {
//		return new ResponseEntity<>(service.getAllUsersPagination(pageNo, 2),HttpStatus.OK);  	
//    }
	
	@PostMapping("/image/{userId}")
	public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage") MultipartFile image, @PathVariable String userId) throws IOException
	{
		String imageName = fileService.uploadFile(image, imageUploadpath);
		UserDto user = service.getOneUser(userId);
		user.setImageName(imageName);
		UserDto userDto = service.updateUser(user, userId);
		ImageResponse imageResponse = ImageResponse.builder()
				.imageName(imageName)
				.message("image is uploaded successfully")
				.success(true)
				.status(HttpStatus.CREATED)
				.build();
		return new ResponseEntity<ImageResponse>(imageResponse,HttpStatus.CREATED);
	}		
//	@PostMapping("/images/{userId}")
//	public ResponseEntity<ImageResponse> uploadMultipleImage(@RequestParam("userImage") MultipartFile[] images, @PathVariable String userId) throws IOException {
//	    List<String> imageNames = new ArrayList<String>();
//
//	    for (MultipartFile image : images) {
//	        String uploadedFileNames = fileService.uploadFile(image, imageUploadpath);
//	        imageNames.add(uploadedFileNames);
//	        
//	    
//	    UserDto user = service.getOneUser(userId);
//	    user.setListOfImage(imageNames); 
//	    UserDto userDto = service.updateUser(user, userId);
//	    }
//	    ImageResponse imageResponse = ImageResponse.builder()
//	            .imageMultipleName(imageNames)
//	            .message("Images are uploaded successfully")
//	            .success(true)
//	            .status(HttpStatus.CREATED)
//	            .build();
//
//	    return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
//	}
	@GetMapping("/image/{userId}")
	public void serveUserImage(@PathVariable String userId,HttpServletResponse response) throws IOException
	{
		UserDto user = service.getOneUser(userId);
		InputStream resource = fileService.getResource(imageUploadpath, user.getImageName());
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}	
	@GetMapping("/login")
	public ResponseEntity<UserDto> loginUser( @RequestParam String email,
		    @RequestParam String password)
	{
		System.err.println(email+" "+password);
		return new ResponseEntity<>(service.loginUser(email, password),HttpStatus.OK);
	}
	
	@GetMapping("/current-user")
	public ResponseEntity<UserDto> getCurrentUser(Principal principal)
	{
		return new ResponseEntity<>(service.getUserByUserName(principal.getName()),HttpStatus.OK);
	}
}