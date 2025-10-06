package com.ConsumerFleetHub.ConsumerFleetHub.Controller;

import com.ConsumerFleetHub.ConsumerFleetHub.DTOs.EndTrip;
import com.ConsumerFleetHub.ConsumerFleetHub.DTOs.ResponseFomTransporter;
import com.ConsumerFleetHub.ConsumerFleetHub.Entities.Consumer;
import com.ConsumerFleetHub.ConsumerFleetHub.Entities.LoadQuery;
import com.ConsumerFleetHub.ConsumerFleetHub.ResponseDTOs.Vehicle;
import com.ConsumerFleetHub.ConsumerFleetHub.Services.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
	public ResponseEntity<Consumer> saveUser(@RequestBody Consumer consumer)
	{
		Consumer c=consumerService.saveUser(consumer);
		return new ResponseEntity<Consumer>(c,HttpStatus.CREATED);
	}
	@PutMapping("/remove/query/{username}")
	public ResponseEntity<Consumer> removeQuery(@PathVariable String username)
	{
		Consumer c = consumerService.removeLoadQuery(username);
		return new ResponseEntity<Consumer>(c,HttpStatus.OK);
		
	}
	@PutMapping("/create/query/{username}") 
	public ResponseEntity<List<Vehicle>> createLoadQuery(@PathVariable String username,@RequestBody LoadQuery lq)
	{
		ResponseEntity<List<Vehicle>> c = consumerService.createLoadQuery(username, lq);
		return c;
		
	}
	@GetMapping("/get/all")
	public ResponseEntity<List<Consumer>> getAllConsumer()
	{
		List<Consumer> listConsumers=consumerService.getAllCosumers();
		return new ResponseEntity<List<Consumer>>(listConsumers,HttpStatus.OK);
	}
	
	@GetMapping("/get/all/pendingQueries")
	public ResponseEntity<List<LoadQuery>> getAllQueries()
	{
		List<LoadQuery> listConsumers=consumerService.getAllPendigsQueries();
		return new ResponseEntity<List<LoadQuery>>(listConsumers,HttpStatus.OK);
	}
    @GetMapping("/get/{username}")
	public ResponseEntity<Consumer> getByUsername(String username)
	{
    	Consumer c = consumerService.getConsumer(username);
    	return new ResponseEntity<Consumer>(c,HttpStatus.OK);
	}
    @GetMapping("/get/consumers/pendingQueries")
    public ResponseEntity<List<Consumer>> getUserswithPendingQueries()
    {
    	List<Consumer> list = consumerService.getAllCosumersWithPendingQueries();
    	return new ResponseEntity<List<Consumer>>(list,HttpStatus.OK);
    }
    @GetMapping("/getVehicle/{userId}/{vehicle_number}")
    public ResponseEntity<Vehicle> postVehicleReuest(@PathVariable String userId,@PathVariable String vehicle_number)
    {
    	return consumerService.bookMyVehicle(vehicle_number, userId);
    }
    @PutMapping("/get/responseFromTransporter")
    public ResponseEntity<ResponseFomTransporter> updateRequetStatus(@RequestBody ResponseFomTransporter responseFomTransporter)
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
    
}
