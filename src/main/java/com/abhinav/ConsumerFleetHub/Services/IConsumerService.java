package com.abhinav.ConsumerFleetHub.Services;

import com.abhinav.ConsumerFleetHub.DTOs.*;
import com.abhinav.ConsumerFleetHub.Entities.Consumer;
import com.abhinav.ConsumerFleetHub.Entities.LoadQuery;
import com.abhinav.ConsumerFleetHub.ResponseDTOs.CreateLoadQueryResponseDto;
import com.abhinav.ConsumerFleetHub.DTOs.VehicleAndTransporterDetails;
import com.abhinav.ConsumerFleetHub.ResponseDTOs.VehicleAndTransporterDetailsResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IConsumerService
{
    Consumer saveUser(ConsumerDto consumerDto, String role);

    Consumer removeLoadQuery(String username);

    List<Consumer> getAllCosumers();

    List<LoadQuery> getAllPendigsQueries();

    Consumer getConsumer(String username);

    List<Consumer> getAllCosumersWithPendingQueries();

    ResponseEntity<CreateLoadQueryResponseDto> createLoadQuery(String username, LoadQueryDto queryDto);

    ResponseEntity<VehicleAndTransporterDetailsResponseDto> bookMyVehicle(String vehicle_number, String userId);

    ResponseFomTransporter updateRequetStatus(ResponseFomTransporter responseFomTransporter);

    ResponseEntity<EndTrip> closeTrip(EndTrip endTrip);
}