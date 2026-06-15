package com.abhinav.ConsumerFleetHub.Entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="RequestToTransporter")
public class RequestToTransporter 
{
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	long id;
	@Column
	String username;
	@Column
	long loads;
	@Column
	String source;
	@Column
	String destination;
	@Column
	boolean isAccepted;
	@JsonBackReference("loadquery-reuqest")
	@OneToOne(mappedBy="requestToTransorter")
	LoadQuery loadQuery;
}
