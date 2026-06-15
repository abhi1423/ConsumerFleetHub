package com.abhinav.ConsumerFleetHub.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ResponseFomTransporter 
{
	String transporter;
	String username;
	boolean response;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserNotFoundDTO
    {
        String msg;
        HttpStatus httpcode;
        boolean isExists;

    }
}
