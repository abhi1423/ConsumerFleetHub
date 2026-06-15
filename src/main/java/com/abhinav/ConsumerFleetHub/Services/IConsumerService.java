package com.abhinav.ConsumerFleetHub.Services;

import com.abhinav.ConsumerFleetHub.DTOs.EndTrip;
import com.abhinav.ConsumerFleetHub.DTOs.ResponseFomTransporter;
import com.abhinav.ConsumerFleetHub.Entities.Consumer;
import com.abhinav.ConsumerFleetHub.Entities.LoadQuery;
import com.abhinav.ConsumerFleetHub.ResponseDTOs.Vehicle;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IConsumerService
{
    Consumer saveUser(Consumer consumer);

    Consumer removeLoadQuery(String username);

    List<Consumer> getAllCosumers();

    List<LoadQuery> getAllPendigsQueries();

    Consumer getConsumer(String username);

    List<Consumer> getAllCosumersWithPendingQueries();

    ResponseEntity<List<Vehicle>> createLoadQuery(String username, LoadQuery query);

    ResponseEntity<Vehicle> bookMyVehicle(String vehicle_number, String userId);

    ResponseFomTransporter updateRequetStatus(ResponseFomTransporter responseFomTransporter);

    ResponseEntity<EndTrip> closeTrip(EndTrip endTrip);
}