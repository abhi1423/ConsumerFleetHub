package com.abhinav.ConsumerFleetHub.Controller;

import com.abhinav.ConsumerFleetHub.DTOs.JwtResponse;
import com.abhinav.ConsumerFleetHub.DTOs.UserLogin;
import com.abhinav.ConsumerFleetHub.Jwt.JwtHelper;
import com.abhinav.ConsumerFleetHub.Services.CustomUserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class JwtLoginController  {

    private final AuthenticationManager authenticationManager;
    private JwtHelper JwtHelper;
    private CustomUserDetailService userDetailService;
    public JwtLoginController(AuthenticationManager authenticationManager,
                              JwtHelper JwtHelper,
                              CustomUserDetailService userDetailService){
        this.authenticationManager = authenticationManager;
        this.JwtHelper = JwtHelper;
        this.userDetailService = userDetailService;
    }

    @GetMapping("/jwtToken")
    public ResponseEntity<JwtResponse> getJwtToken(@RequestBody UserLogin userLogin){
        authenticate(userLogin);
        UserDetails userDetails = userDetailService.loadUserByUsername(userLogin.getEmail());
        String token = JwtHelper.generateToken(userDetails);
        JwtResponse jwtResponse = JwtResponse.builder().token(token).build();
        return new ResponseEntity<>(jwtResponse, HttpStatus.CREATED);
    }

    private void authenticate(UserLogin userLogin) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userLogin.getEmail(),userLogin.getPassword());
        try{
            authenticationManager.authenticate(token);
        }
        catch (Exception e){
            throw new BadCredentialsException("Bad credentials");
        }

    }

}
