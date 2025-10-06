package com.ConsumerFleetHub.ConsumerFleetHub.ExceptionDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserNotFoundDTO 
{
	String msg;
	HttpStatus httpcode;
	boolean isExists;

}
