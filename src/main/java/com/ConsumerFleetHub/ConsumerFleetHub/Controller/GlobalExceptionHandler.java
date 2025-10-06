package com.ConsumerFleetHub.ConsumerFleetHub.Controller;

import com.ConsumerFleetHub.ConsumerFleetHub.ExceptionDTOs.LoadqueryExistsDTO;
import com.ConsumerFleetHub.ConsumerFleetHub.ExceptionDTOs.LoadqueryNotFoundDTO;
import com.ConsumerFleetHub.ConsumerFleetHub.ExceptionDTOs.UserAlreadyExistsDTO;
import com.ConsumerFleetHub.ConsumerFleetHub.ExceptionDTOs.UserNotFoundDTO;
import com.ConsumerFleetHub.ConsumerFleetHub.Exceptions.LoadqueryAlreadyExistsException;
import com.ConsumerFleetHub.ConsumerFleetHub.Exceptions.LoadqueryNotFoundException;
import com.ConsumerFleetHub.ConsumerFleetHub.Exceptions.UserAlreadyExistsException;
import com.ConsumerFleetHub.ConsumerFleetHub.Exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler 
{
	@ExceptionHandler(LoadqueryAlreadyExistsException.class)
	public ResponseEntity<LoadqueryExistsDTO> loadqueryInQueue(LoadqueryAlreadyExistsException exp)
	{
		LoadqueryExistsDTO dto=LoadqueryExistsDTO.builder().msg("This query is already in queue")
				.httpcode(HttpStatus.BAD_GATEWAY).isExists(true).build();
		return new ResponseEntity<LoadqueryExistsDTO>(dto,HttpStatus.BAD_GATEWAY);
	}
	@ExceptionHandler(LoadqueryNotFoundException.class)
	public ResponseEntity<LoadqueryNotFoundDTO> loadqueryNotFound(LoadqueryNotFoundException exp)
	{
		LoadqueryNotFoundDTO dto=LoadqueryNotFoundDTO.builder().msg("No reuqest found")
				.httpcode(HttpStatus.NOT_FOUND).isExists(true).build();
		return new ResponseEntity<LoadqueryNotFoundDTO>(dto,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<UserAlreadyExistsDTO> UserAlreadyInDB(UserAlreadyExistsException exp)
	{
		UserAlreadyExistsDTO dto=UserAlreadyExistsDTO.builder().msg("User is already in database with this id/username")
				.httpcode(HttpStatus.BAD_GATEWAY).isExists(true).build();
		return new ResponseEntity<UserAlreadyExistsDTO>(dto,HttpStatus.BAD_GATEWAY);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<UserNotFoundDTO> UserNotFound(UserNotFoundDTO exp)
	{
		UserNotFoundDTO dto=UserNotFoundDTO.builder().msg("User not found")
				.httpcode(HttpStatus.NOT_FOUND).isExists(true).build();
		return new ResponseEntity<UserNotFoundDTO>(dto,HttpStatus.NOT_FOUND);
	}
}
