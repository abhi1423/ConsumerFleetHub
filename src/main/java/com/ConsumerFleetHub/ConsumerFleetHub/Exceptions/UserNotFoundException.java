package com.ConsumerFleetHub.ConsumerFleetHub.Exceptions;

public class UserNotFoundException extends RuntimeException{
	public UserNotFoundException() {
		super("User not in DB");
		
	}
	public UserNotFoundException(String msg)
	{
		super(msg);
	}

}
