package com.ConsumerFleetHub.ConsumerFleetHub.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoadqueryExistsDTO 
{
	String msg;
	HttpStatus httpcode;
	boolean isExists;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoadqueryNotFoundDTO {
        String msg;
        HttpStatus httpcode;
        boolean isExists;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserAlreadyExistsDTO
    {
        String msg;
        HttpStatus httpcode;
        boolean isExists;

    }
}
