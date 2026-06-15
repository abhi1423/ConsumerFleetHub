package com.abhinav.ConsumerFleetHub.Exceptions;

public class LoadqueryNotFoundException extends RuntimeException {
	public LoadqueryNotFoundException() {
		super("Query alreay in queue");
	}
	public LoadqueryNotFoundException(String msg)
	{
		super(msg);
	}
	
	
}
