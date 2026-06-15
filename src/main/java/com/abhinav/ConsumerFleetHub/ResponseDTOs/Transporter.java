package com.abhinav.ConsumerFleetHub.ResponseDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transporter
{
	String id;
	String password;
	String username;
	String phoneNumber;
	String city;
	String pincode;
}
