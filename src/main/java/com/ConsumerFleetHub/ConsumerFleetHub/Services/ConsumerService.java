package com.ConsumerFleetHub.ConsumerFleetHub.Services;

import com.ConsumerFleetHub.ConsumerFleetHub.DTOs.EndTrip;
import com.ConsumerFleetHub.ConsumerFleetHub.DTOs.ResponseFomTransporter;
import com.ConsumerFleetHub.ConsumerFleetHub.Entities.Consumer;
import com.ConsumerFleetHub.ConsumerFleetHub.Entities.LoadQuery;
import com.ConsumerFleetHub.ConsumerFleetHub.Entities.RequestStatus;
import com.ConsumerFleetHub.ConsumerFleetHub.Entities.RequestToTransporter;
import com.ConsumerFleetHub.ConsumerFleetHub.Exceptions.UserAlreadyExistsException;
import com.ConsumerFleetHub.ConsumerFleetHub.Exceptions.UserNotFoundException;
import com.ConsumerFleetHub.ConsumerFleetHub.Repositories.ConsumerRepository;
import com.ConsumerFleetHub.ConsumerFleetHub.Repositories.LoadqueriesRepository;
import com.ConsumerFleetHub.ConsumerFleetHub.Repositories.RequestRepository;
import com.ConsumerFleetHub.ConsumerFleetHub.ResponseDTOs.Vehicle;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
public class ConsumerService implements IConsumerService
{
    private  ConsumerRepository consumerRepository;

    private  LoadqueriesRepository loadqueriesRepository;

    private  RequestRepository requestRepository;

    private  RestTemplate restTemplate;

    private PasswordEncoder passwordEncoder;

    public ConsumerService(
            ConsumerRepository consumerRepository,
            LoadqueriesRepository loadqueriesRepository,
            RequestRepository requestRepository,
            RestTemplate restTemplate,
            PasswordEncoder passwordEncoder)
    {
        this.consumerRepository = consumerRepository;
        this.loadqueriesRepository = loadqueriesRepository;
        this.requestRepository = requestRepository;
        this.restTemplate = restTemplate;
        this.passwordEncoder = passwordEncoder;
    }

	public Consumer saveUser(Consumer consumer)
	{
		String id=UUID.randomUUID().toString();
		consumer.setId(id);
		Consumer c=consumerRepository.findByUsername(consumer.getUsername()).orElse(null);
		if(c!=null)
		{
			throw new UserAlreadyExistsException("User is already in DB");
		}
		LoadQuery lq=consumer.getQuery();
		if(lq!=null)
		{
			lq.setId(UUID.randomUUID().toString());
			lq.setConsumer(consumer);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode(consumer.getPassword());
        consumer.setPassword(password);
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

	public List<Consumer> getAllCosumersWithPendingQueries()
	{
		return consumerRepository.findUsersWithPendingQueries();
	}
	
	@Transactional
	public ResponseEntity<List<Vehicle>> createLoadQuery(String username,LoadQuery query)
	{
		String city=query.getSource();
		long loadRequired=query.getLoadInTons();
		Consumer c=consumerRepository.findByUsername(username).orElse(null);
		if(c==null)
		{
			throw new UserNotFoundException("User not found with this username");
		}
		String id=UUID.randomUUID().toString();
		query.setId(id);
		c.setQuery(query);
		consumerRepository.save(c);
		System.out.println("City name is "+city);
		String url="http://GOOD-CARRIER/transporter/get/vehilceFromCity/"+city+"/"+loadRequired; 
		ResponseEntity<List<Vehicle>> vehicles = restTemplate.exchange(url,HttpMethod.GET,null,new ParameterizedTypeReference<List<Vehicle>>() {});
		return vehicles;
	}

	
	public ResponseEntity<Vehicle> bookMyVehicle(String vehicle_number, String userId)
    {
    	String url = "http://GOOD-CARRIER/transporter/bookMyVehicle/"+vehicle_number;
    	Consumer user = consumerRepository.findByUsername(userId).orElse(null);
    	RequestToTransporter requestToTransporter= RequestToTransporter.builder().username(user.getUsername()).loads(user.getQuery()
    			.getLoadInTons()).source(user.getQuery().getSource()).
    			destination(user.getQuery().getDestination())
    			.isAccepted(false).loadQuery(user.getQuery()).build();
    	LoadQuery lq = user.getQuery();
    	lq.setRequestToTransorter(requestToTransporter);
    	loadqueriesRepository.save(lq);
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	HttpEntity<RequestToTransporter> request = new HttpEntity<>(requestToTransporter, headers);
    	ResponseEntity<Vehicle> response = restTemplate.exchange(url, HttpMethod.POST,request,Vehicle.class);
    	return response;
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
}
