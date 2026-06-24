package com.abhinav.ConsumerFleetHub.Controller;

import com.abhinav.ConsumerFleetHub.DTOs.*;
import com.abhinav.ConsumerFleetHub.Entities.Consumer;
import com.abhinav.ConsumerFleetHub.Entities.LoadQuery;
import com.abhinav.ConsumerFleetHub.Entities.Role;
import com.abhinav.ConsumerFleetHub.ResponseDTOs.CreateLoadQueryResponseDto;
import com.abhinav.ConsumerFleetHub.DTOs.VehicleAndTransporterDetails;
import com.abhinav.ConsumerFleetHub.ResponseDTOs.VehicleAndTransporterDetailsResponseDto;
import com.abhinav.ConsumerFleetHub.Services.ConsumerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/consumer")
public class ConsumerController 
{
	@Autowired
    ConsumerService consumerService;
	
	/* saveUser
	 * removeLoadQuery
	 * createLoadQuery
	 * getAllCosumers
	 * getAllQueries
    */
	@PostMapping("/add")
	public ResponseEntity<String> saveUser(@RequestBody ConsumerDto consumerDto)
	{
		Consumer c=consumerService.saveUser(consumerDto,"ROLE_CONSUMER");
		return new ResponseEntity<>("Consumer created successfully",HttpStatus.CREATED);
	}

	@PutMapping("/remove/query/{username}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CONSUMER')")
	public ResponseEntity<String> removeQuery(@PathVariable String username)
	{
		Consumer c = consumerService.removeLoadQuery(username);
		return new ResponseEntity<>("The LoadQuery has been  removed successfully",HttpStatus.OK);
		
	}
    @PreAuthorize("hasRole('ADMIN') or hasRole('CONSUMER')")
	@PutMapping("/create/query/{username}") 
	public ResponseEntity<CreateLoadQueryResponseDto> createLoadQuery(@PathVariable String username, @Valid @RequestBody LoadQueryDto lq) {
        return consumerService.createLoadQuery(username, lq);
		
	}
    @PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/get/all")
	public ResponseEntity<List<Consumer>> getAllConsumer()
	{
		List<Consumer> listConsumers=consumerService.getAllCosumers();
		return new ResponseEntity<List<Consumer>>(listConsumers,HttpStatus.OK);
	}
    @PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/get/all/pendingQueries")
	public ResponseEntity<List<LoadQuery>> getAllQueries()
	{
		List<LoadQuery> listConsumers=consumerService.getAllPendigsQueries();
		return new ResponseEntity<List<LoadQuery>>(listConsumers,HttpStatus.OK);
	}

    @PreAuthorize("hasRole('ADMIN') or hasRole('CONSUMER')")
    @GetMapping("/get/{username}")
	public ResponseEntity<Consumer> getByUsername(@PathVariable String username)
	{
    	Consumer c = consumerService.getConsumer(username);
    	return new ResponseEntity<Consumer>(c,HttpStatus.OK);
	}

    @PreAuthorize("hasRole('ADMIN') or hasRole('CONSUMER')")
    @GetMapping("/get/consumers/pendingQueries")
    public ResponseEntity<List<Consumer>> getUsersWithPendingQueries() {
    	List<Consumer> list = consumerService.getAllCosumersWithPendingQueries();
    	return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CONSUMER')")
    @PostMapping("/bookVehicle/{userId}/{vehicle_number}")
    public ResponseEntity<VehicleAndTransporterDetailsResponseDto> postVehicleRequest(@PathVariable String userId, @PathVariable String vehicle_number)
    {
    	return consumerService.bookMyVehicle(vehicle_number, userId);
    }
    
    @PutMapping("/get/responseFromTransporter")
    public ResponseEntity<ResponseFomTransporter> updateRequestStatus(@RequestBody ResponseFomTransporter responseFomTransporter)
    {
    	System.out.println("The response from transporter is "+responseFomTransporter.isResponse());
    	ResponseFomTransporter response=consumerService.updateRequetStatus(responseFomTransporter);
    	return new ResponseEntity<ResponseFomTransporter>(response,HttpStatus.CREATED);
    }
    
    @PutMapping("/closeTrip")
    public ResponseEntity<EndTrip> closeTrip(@RequestBody EndTrip endTrip)
    {
    	return consumerService.closeTrip(endTrip);
    }
    @PostMapping("/add/admin")
    public ResponseEntity<String> saveUserAsAdmin(@RequestBody ConsumerDto consumerDto)
    {
        Consumer c=consumerService.saveUser(consumerDto,"ROLE_ADMIN");
        Set<Role> roles = c.getRoles();
        roles.clear();
        return new ResponseEntity<>("Consumer created successfully",HttpStatus.CREATED);
    }
    
}
