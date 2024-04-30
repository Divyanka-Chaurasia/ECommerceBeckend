package com.project.app.serviceimpl;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.project.app.dtos.PageableResponse;
import com.project.app.dtos.UserDto;
import com.project.app.entity.User;
import com.project.app.exception.DuplicateEntryException;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.helper.Helper;
import com.project.app.iservices.IUserServices;
import com.project.app.repositories.UserRepo;
@Service
public class UserServiceImpl implements IUserServices {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper mapper;	
	@Value("${user.profile.image.path}")
	private String imagepath;
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Override
	public UserDto createUser(UserDto userDto) {
	String newUserEmail = userDto.getEmail();         	
	            if (userRepo.existsByEmail(newUserEmail) ) {
	                throw new DuplicateEntryException("email is duplicate");
	        }
		String userId=UUID.randomUUID().toString();
		User user1 = dtoToEntity(userDto);
		user1.setUserId(userId);
		User savedUser  = userRepo.save(user1);
		UserDto newDto = entityToDto(savedUser);	
		return newDto;
	}
	@Override
	public UserDto updateUser(UserDto userDto, String userId) {
		
		User user = userRepo.findById(userId).orElseThrow(
				()->new ResourceNotFoundException("user not found")
				);
		user.setName(userDto.getName());	
		user.setAbout(userDto.getAbout());	
		user.setGender(userDto.getGender());	
		user.setImageName(userDto.getImageName());
		
		//user.setListOfImage(userDto.getListOfImage());
		
		  User updatedUser = userRepo.save(user);
	      UserDto UpdatedDtao = mapper.map(updatedUser, UserDto.class);
	      return UpdatedDtao;
		  
		/* userDto.setUserId(userId);
		 * if(userDto.getUserId()==null || !userRepo.existsById(userDto.getUserId())) {
		 * throw new UserNotFoundException("user id is not found"); } else { //userDto t
		 * user entity User user = dtoToEntity(userDto); User savedUser =
		 * userRepo.save(user); //user entity to userDto UserDto newDto =
		 * entityToDto(savedUser);
		 * 
		 * return newDto; }
		 */
	}

	@Override
	public void deleteUser(String userId) {
		User user = userRepo.findById(userId).orElseThrow(
				()-> new ResourceNotFoundException("user not found")
				);
		String fullPath = imagepath+user.getImageName();
		Path path=Paths.get(fullPath);
		try {
			Files.delete(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		userRepo.delete(user);
	}

//	@Override
//	public List<UserDto> getAllUsers() {
//
//		List<User> user = userRepo.findAll();
//		System.out.println(user.toString());
//		List<UserDto> usersDto = user.stream()
//				.map((User)->mapper               //.map((x)->mapper and .map(x,UserDto.class) ka x name same hona chahiye
//				.map(User, UserDto.class))
//				.collect(Collectors.toList());
//		return usersDto;
//		
//		
//		/*
//		 * List<User> user = userRepo.findAll(); List<UserDto> userDtos = new
//		 * ArrayList<>(); for(User u:user) { UserDto userDto =entityToDto(u);
//		 * userDtos.add(userDto); }
//		 * 
//		 * return userDtos;
//		 */
//	}

	@Override
	public UserDto getOneUser(String userIds) {
		User user = userRepo.findById(userIds).orElseThrow(
				()-> new ResourceNotFoundException("user not found")
				);
		return mapper.map(user, UserDto.class);
	}
	@Override
	public List<UserDto> searchUser(String keyword) {
		List<User> users = userRepo.findByNameContaining(keyword);
		List<UserDto> usersDto = users.stream().map((User)->mapper
				.map(User, UserDto.class))
				.collect(Collectors.toList());
		return usersDto;
	}
	private UserDto entityToDto(User savedUser) {
//		UserDto userDto = UserDto.builder()
//				.userId(savedUser.getUserId())
//				.name(savedUser.getName())
//				.email(savedUser.getEmail())
//				.password(savedUser.getPassword())
//				.gender(savedUser.getGender())
//				.about(savedUser.getAbout())
//				.imageName(savedUser.getImageName())
//				.build();
//		return userDto;
		
		return mapper.map(savedUser, UserDto.class);
	}
	private User dtoToEntity(UserDto userDto) {
//		User user = User.builder()
//				.userId(userDto.getUserId())
//				.name(userDto.getName())
//				.email(userDto.getEmail())
//				.password(userDto.getPassword())
//				.gender(userDto.getGender())
//				.about(userDto.getAbout())
//				.imageName(userDto.getImageName())
//				.build();
//		return user;
		
		return mapper.map(userDto, User.class);
	}
	@Override
	public Page<UserDto> getAllUsersPagination(int currentPage, int page) {
		Pageable pageable = PageRequest.of(currentPage, page);
		Page<User> page1 =  userRepo.findAll(pageable);
		List<UserDto> usersDto =  page1.getContent()
				.stream()
				.map(User->mapper .map(User, UserDto.class))         //.map((x)->mapper and .map(x,UserDto.class) ka x name same hona chahiye
				.collect(Collectors.toList());
		return new PageImpl<>(usersDto);
	}
	@Override
	public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDirection) {
		Sort sort = (sortDirection.equalsIgnoreCase("desc"))?
				(Sort.by(sortBy).descending()):
					(Sort.by(sortBy).ascending());
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		Page<User> page = userRepo.findAll(pageable);
		PageableResponse<UserDto> response = Helper.getPageableResponse(page,UserDto.class);
		return response;
	}
	@Override
	public UserDto loginUser(String email, String passWord) {
	    Optional<User> user = userRepo.findByEmailAndPassword(email, passWord);
	    System.err.println(user);
	    if (user.isPresent()) 
	    {
	        UserDto userDto = mapper.map(user.get(), UserDto.class);  
	        return userDto;
	    } else 
	    {
	        throw new ResourceNotFoundException("User is not present");
	    }
	}
	@Override
	public UserDto getUserByUserName(String name) {
		User user = userRepo.findByName(name);
		UserDto userDto = mapper.map(user, UserDto.class);
		return userDto;
	}
}