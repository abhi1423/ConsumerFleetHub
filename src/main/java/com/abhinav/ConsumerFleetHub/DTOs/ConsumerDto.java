package com.abhinav.ConsumerFleetHub.DTOs;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerDto {

    @NotBlank(message = "Username cannot be null or empty")
    private String username;

    @NotBlank(message = "Password cannot be null or empty")
    private String password;

}