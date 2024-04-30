package com.project.app.handlers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.project.app.dtos.ApiResponseMessage;
import com.project.app.dtos.BadApiRequestException;
import com.project.app.exception.DuplicateEntryException;
import com.project.app.exception.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseMessage> resourceNotFoundException(ResourceNotFoundException e)
	{
		logger.info("exception hanlder is line");
		ApiResponseMessage response = ApiResponseMessage.builder() 
				.msg(e.getMessage()) 
				.status(HttpStatus.NOT_FOUND) 
				.success(false).build();
		
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
	{
		//logger.info("MethodArgumentNotValidException hanlder is line");
		List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
		Map<String,Object> response = new HashMap<>();
		allErrors.stream().forEach(objectError->{
			String message = objectError.getDefaultMessage();
			String field =  ((FieldError) objectError).getField();
			response.put(field, message);
		});
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BadApiRequestException.class)
	public ResponseEntity<ApiResponseMessage> badResponseException(BadApiRequestException b)
	{
		logger.info("badResponseException hanlder is line");
		ApiResponseMessage response = ApiResponseMessage.builder() 
				.msg(b.getMessage()) 
				.status(HttpStatus.BAD_REQUEST) 
				.success(true).build();
		
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DuplicateEntryException.class)
	public ResponseEntity<ApiResponseMessage> duplicateEntryException(DuplicateEntryException ex)
	{
		ApiResponseMessage response = ApiResponseMessage.builder() 
				.msg(ex.getMessage()) 
				.status(HttpStatus.BAD_REQUEST) 
				.success(true).build();
		
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}	
}