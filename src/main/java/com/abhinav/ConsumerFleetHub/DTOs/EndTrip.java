package com.abhinav.ConsumerFleetHub.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndTrip 
{
	String remarks;
	boolean closed;
	String username;
}
