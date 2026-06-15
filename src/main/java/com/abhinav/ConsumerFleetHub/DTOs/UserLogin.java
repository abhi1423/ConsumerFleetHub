package com.abhinav.ConsumerFleetHub.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLogin {
    @NotNull(message = "email is mandatory field")
    String email;
    @NotNull(message = "password is mandatory field")
    String password;
}
