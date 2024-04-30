package com.project.app.dtos;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDto {
	private String userId;
	@Size(min = 3,max = 30,message = "invalid name")
	private String name;
	@Email(message = "Invalid user email")
	@NotBlank(message = "email is required")
	@Pattern(
	    regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$",
	    message = "invalid email"
	)
	private String email;	
	@Pattern(
		    regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
		    message = "password must contain at least one uppercase, one lowercase, and one digit"
		)
    @NotBlank(message = "password is required")
	private String password;
	
	private String gender;	
	@NotBlank(message = "Write something !!")
	@Size(min = 10 , max = 100)
	private String about;
	//@ImageNameValid(message = "invalid message")	
	private String imageName;
	
	@NotBlank(message = "required")
	@Pattern(
			regexp = "[0-9]*$",
			message = "invalid mobile"
			)
	@Size(min= 10 , max = 10)
	private String mobile;
	
	@NotBlank(message = "required")
	@Pattern(
			 regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
			message = "invalid"
			)
	private String confirmPassword;
	//private List<String> listOfImage; 	
}