package com.ConsumerFleetHub.ConsumerFleetHub.Exceptions;


public class LoadqueryAlreadyExistsException extends RuntimeException{
	public LoadqueryAlreadyExistsException()
	{
		super("Vehicle already exists");
	}
	
	public LoadqueryAlreadyExistsException(String msg)
	{
		super(msg);
	}

}
