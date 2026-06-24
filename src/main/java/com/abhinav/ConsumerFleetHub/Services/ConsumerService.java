package com.abhinav.ConsumerFleetHub.Services;

import com.abhinav.ConsumerFleetHub.DTOs.*;
import com.abhinav.ConsumerFleetHub.Entities.*;
import com.abhinav.ConsumerFleetHub.Exceptions.UserAlreadyExistsException;
import com.abhinav.ConsumerFleetHub.Exceptions.UserNotFoundException;
import com.abhinav.ConsumerFleetHub.Repositories.ConsumerRepository;
import com.abhinav.ConsumerFleetHub.Repositories.LoadqueriesRepository;
import com.abhinav.ConsumerFleetHub.Repositories.RequestRepository;

import com.abhinav.ConsumerFleetHub.ResponseDTOs.CreateLoadQueryResponseDto;
import com.abhinav.ConsumerFleetHub.DTOs.VehicleAndTransporterDetails;
import com.abhinav.ConsumerFleetHub.ResponseDTOs.VehicleAndTransporterDetailsResponseDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;

import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class ConsumerService implements IConsumerService
{
    private  ConsumerRepository consumerRepository;

    private  LoadqueriesRepository loadqueriesRepository;

    private  RequestRepository requestRepository;

    private WebClient.Builder webClient;

    private PasswordEncoder passwordEncoder;

    public ConsumerService(
            ConsumerRepository consumerRepository,
            LoadqueriesRepository loadqueriesRepository,
            RequestRepository requestRepository,
            WebClient.Builder webClient,
            PasswordEncoder passwordEncoder)
    {
        this.consumerRepository = consumerRepository;
        this.loadqueriesRepository = loadqueriesRepository;
        this.requestRepository = requestRepository;
        this.webClient = webClient;
        this.passwordEncoder = passwordEncoder;
    }

	public Consumer saveUser(ConsumerDto consumerDto, String r)
	{

		String id=UUID.randomUUID().toString();
		//consumer.setId(id);
		Consumer c=consumerRepository.findByUsername(consumerDto.getUsername()).orElse(null);
		if(c!=null)
		{
			throw new UserAlreadyExistsException("User is already in DB");
		}

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode(consumerDto.getPassword());
        Role role = new Role();
        role.setRoleName(r);
        Consumer consumer = Consumer.builder().id(id)
                .username(consumerDto.getUsername())
                .password(password)
                .roles(Set.of(role))
                .build();
		return consumerRepository.save(consumer);
	}


    public Consumer removeLoadQuery(String username)
	{
		Consumer c=consumerRepository.findByUsername(username).orElse(null);
		if(c==null)
		{
			throw new UserNotFoundException("User not found with this username");
		}
		c.setQuery(null);
		consumerRepository.save(c);
		return c;
	}
	public List<Consumer> getAllCosumers()
	{
		return consumerRepository.findAll();
	}

	public List<LoadQuery> getAllPendigsQueries()
	{
		return consumerRepository.findUnresolvedQueries();
	}

	public Consumer getConsumer(String username)
	{
		Consumer c = consumerRepository.findByUsername(username).orElse(null);
		if(c==null)
		{
			throw new UserNotFoundException("User not found");
		}
		return c;
	}

	public List<Consumer> getAllCosumersWithPendingQueries() {
		return consumerRepository.findUsersWithPendingQueries();
	}

	@Transactional
    @CircuitBreaker(name = "fleetHubCB", fallbackMethod = "fallBackCreateLoadQuery")
	public ResponseEntity<CreateLoadQueryResponseDto> createLoadQuery(String username, LoadQueryDto queryDto)
	{


		Consumer c=consumerRepository.findByUsername(username).orElse(null);
		if(c==null)
		{
			throw new UserNotFoundException("User not found with this username");
		}
        LoadQuery loadQuery = LoadQuery.builder()
                .id(UUID.randomUUID().toString())
                .loadInTons(queryDto.getLoadInTons())
                .source(queryDto.getSource())
                .destination(queryDto.getDestination())
                .isResolved(false)
                .status(RequestStatus.inProgress)
                .build();
		c.setQuery(loadQuery);
		consumerRepository.save(c);


        List<VehicleAndTransporterDetails> vehicles = webClient.build().get()
                .uri("http://Fleet-Hub/transporter/get/vehicleFromCity/{city}/{loadRequired}",queryDto.getSource(),queryDto.getLoadInTons())
                .retrieve()
                .bodyToFlux(VehicleAndTransporterDetails.class)
                .collectList()
                .block();

        CreateLoadQueryResponseDto dto = CreateLoadQueryResponseDto.builder()
                .msg("Data fetch successfully")
                .vehicleAndTransporterDetailsList(vehicles)
                .status(String.valueOf(HttpStatus.OK))
                .build();
        return new ResponseEntity<>(dto,HttpStatus.OK);
	}

    @CircuitBreaker(name = "fleetHubCB", fallbackMethod = "fallBackBookMyVehicle")
	public ResponseEntity<VehicleAndTransporterDetailsResponseDto> bookMyVehicle(String vehicle_number, String userId)
    {
    	Consumer user = consumerRepository.findByUsername(userId).orElse(null);
    	RequestToTransporter requestToTransporter= RequestToTransporter.builder().username(user.getUsername()).loads(user.getQuery()
    			.getLoadInTons()).source(user.getQuery().getSource()).
    			destination(user.getQuery().getDestination())
    			.isAccepted(false).loadQuery(user.getQuery()).build();

    	LoadQuery lq = user.getQuery();
    	lq.setRequestToTransorter(requestToTransporter);
    	loadqueriesRepository.save(lq);

        VehicleAndTransporterDetails getVehicle = webClient.build()
                .post()
                .uri("http://Fleet-Hub/transporter/bookMyVehicle/{vehicle_number}",vehicle_number)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestToTransporter)
                .retrieve()
                .bodyToMono(VehicleAndTransporterDetails.class)
                .block();
        VehicleAndTransporterDetailsResponseDto dto = VehicleAndTransporterDetailsResponseDto.builder()
                .vehicleAndTransporterDetails(getVehicle)
                .msg("Request success")
                .build();
    	return new ResponseEntity<>(dto,HttpStatus.OK);
    }
	
	@Transactional
	public ResponseFomTransporter updateRequetStatus(ResponseFomTransporter responseFomTransporter)
	{
		Consumer c=consumerRepository.findByUsername(responseFomTransporter.getUsername()).orElse(null);
		if(c==null)
		{
			throw new UserNotFoundException("User not found with this username");
		}
		LoadQuery lq=c.getQuery();
		if(responseFomTransporter.isResponse())
		{
			RequestToTransporter request = lq.getRequestToTransorter();
			request.setAccepted(true);
			lq.setStatus(RequestStatus.inProgress);
			lq.setRequestToTransorter(request);
			c.setQuery(lq);
			consumerRepository.save(c);
		}
		return responseFomTransporter;
	}
	public ResponseEntity<EndTrip> closeTrip(EndTrip endTrip) {
		Consumer c=consumerRepository.findByUsername(endTrip.getUsername()).orElse(null);
		if(c==null)
		{
			throw new UserNotFoundException("User not found with this username");
		}
		ResponseEntity<EndTrip> response= new ResponseEntity<EndTrip>(endTrip,HttpStatus.BAD_REQUEST);
		LoadQuery lq=c.getQuery();
		if(endTrip.isClosed())
		{
			lq.setResolved(true);
			lq.setStatus(RequestStatus.closed);
			c.setQuery(null);
			loadqueriesRepository.save(lq);
			consumerRepository.save(c);
			response= new ResponseEntity<EndTrip>(endTrip,HttpStatus.CREATED);
			
		}
		return response;
		
	}

    private ResponseEntity<CreateLoadQueryResponseDto> fallBackCreateLoadQuery(){
        List<VehicleAndTransporterDetails> vehicleAndTransporterDetailsList = new ArrayList<>();
        CreateLoadQueryResponseDto dto = CreateLoadQueryResponseDto.builder()
                .msg("Unable to send request to fleetHub service as it is unavailable")
                .vehicleAndTransporterDetailsList(vehicleAndTransporterDetailsList)
                .status(String.valueOf(HttpStatus.NOT_FOUND))
                .build();
        return new ResponseEntity<>(dto,HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<VehicleAndTransporterDetailsResponseDto> fallBackBookMyVehicle() {
        VehicleAndTransporterDetailsResponseDto vehicleAndTransporterDetailsResponseDto =
                VehicleAndTransporterDetailsResponseDto.builder()
                        .msg("Service Down")
                        .vehicleAndTransporterDetails(new VehicleAndTransporterDetails())
                        .build();

        return new ResponseEntity<>(vehicleAndTransporterDetailsResponseDto,HttpStatus.NOT_FOUND);
    }
}
