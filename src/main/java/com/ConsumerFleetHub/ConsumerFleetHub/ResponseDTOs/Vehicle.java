package com.ConsumerFleetHub.ConsumerFleetHub.ResponseDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Vehicle {

	String carrierNumber;
	String carrierCategory;
	String fuelType;
	boolean IsAvailable;
	String model;
	int numberOfAxcels;
	long capacityloadInTonsMin;
	long capacityloadInTonsMax;
	Transporter transporter;
}
