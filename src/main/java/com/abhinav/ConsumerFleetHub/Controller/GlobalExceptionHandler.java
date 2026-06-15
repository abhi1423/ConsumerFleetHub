package com.abhinav.ConsumerFleetHub.Controller;

import com.abhinav.ConsumerFleetHub.DTOs.LoadqueryExistsDTO;
import com.abhinav.ConsumerFleetHub.DTOs.ResponseFomTransporter;
import com.abhinav.ConsumerFleetHub.Exceptions.LoadqueryAlreadyExistsException;
import com.abhinav.ConsumerFleetHub.Exceptions.LoadqueryNotFoundException;
import com.abhinav.ConsumerFleetHub.Exceptions.UserAlreadyExistsException;
import com.abhinav.ConsumerFleetHub.Exceptions.UserNotFoundException;
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
	public ResponseEntity<LoadqueryExistsDTO.LoadqueryNotFoundDTO> loadqueryNotFound(LoadqueryNotFoundException exp)
	{
		LoadqueryExistsDTO.LoadqueryNotFoundDTO dto= LoadqueryExistsDTO.LoadqueryNotFoundDTO.builder().msg("No reuqest found")
				.httpcode(HttpStatus.NOT_FOUND).isExists(true).build();
		return new ResponseEntity<LoadqueryExistsDTO.LoadqueryNotFoundDTO>(dto,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<LoadqueryExistsDTO.UserAlreadyExistsDTO> UserAlreadyInDB(UserAlreadyExistsException exp)
	{
		LoadqueryExistsDTO.UserAlreadyExistsDTO dto= LoadqueryExistsDTO.UserAlreadyExistsDTO.builder().msg("User is already in database with this id/username")
				.httpcode(HttpStatus.BAD_GATEWAY).isExists(true).build();
		return new ResponseEntity<LoadqueryExistsDTO.UserAlreadyExistsDTO>(dto,HttpStatus.BAD_GATEWAY);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ResponseFomTransporter.UserNotFoundDTO> UserNotFound(ResponseFomTransporter.UserNotFoundDTO exp)
	{
		ResponseFomTransporter.UserNotFoundDTO dto= ResponseFomTransporter.UserNotFoundDTO.builder().msg("User not found")
				.httpcode(HttpStatus.NOT_FOUND).isExists(true).build();
		return new ResponseEntity<ResponseFomTransporter.UserNotFoundDTO>(dto,HttpStatus.NOT_FOUND);
	}
}
