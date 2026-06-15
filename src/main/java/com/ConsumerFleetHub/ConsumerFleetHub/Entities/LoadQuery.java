package com.ConsumerFleetHub.ConsumerFleetHub.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="LoadQueries")
public class LoadQuery 
{
	@Id
	@Column
	String id;
	@Column
	@NotNull
	String source;
	@NotNull
	@Column
	String destination;
	@NotNull
	@Column
	long loadInTons;
	@NotNull
	@Column 
	boolean isResolved;
	@NotNull
	@Column
	@Enumerated(EnumType.STRING)
	RequestStatus status;
	@OneToOne(mappedBy="query")
	@JsonBackReference("consumer-query")
	Consumer consumer;
	@JsonManagedReference("loadquery-reuqest")
	@OneToOne(cascade=CascadeType.ALL)
	RequestToTransporter requestToTransorter;
}
