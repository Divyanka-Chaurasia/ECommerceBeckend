package com.project.app.iservices;

import java.util.List;

import org.springframework.data.domain.Page;

import com.project.app.dtos.PageableResponse;
import com.project.app.dtos.UserDto;

/***
 * @author dell
 */
public interface IUserServices {

	/***
	 * create
	 * @param userDto
	 * @return
	 */
	 UserDto createUser(UserDto userDto);
	
	/***
	 * update
	 * @param userDto
	 * @param userId
	 * @return
	 */
	 UserDto updateUser(UserDto userDto,String userId);
	 
	/***
	 * delete
	 * @param userId
	 */
	void deleteUser(String userId);
	
	/***
	 * get all user
	 * @param sortDirection 
	 * @param sortBy 
	 * @param pageSize 
	 * @param pageNumber 
	 * @return
	 */
	PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDirection);
	
	/***
	 * get one user
	 * @param userIds
	 * @return
	 */
	UserDto getOneUser(String userIds);
	
	/***
	 * search user
	 * @param keyword
	 * @return
	 */
	List<UserDto> searchUser(String keyword);
	
	/***
	 * pagination methods for get all users
	 * @param currentPage
	 * @param page
	 * @return
	 */
	public Page<UserDto> getAllUsersPagination(int currentPage,int page);
	
	/***
	 * Login user
	 * @param email
	 * @param passWord
	 * @return
	 */
	public UserDto loginUser(String email,String passWord);

	
	/***
	 * get user by username
	 * @param name
	 * @return
	 */
	public UserDto getUserByUserName(String name);
}