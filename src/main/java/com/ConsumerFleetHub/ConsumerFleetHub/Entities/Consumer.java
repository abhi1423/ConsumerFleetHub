package com.ConsumerFleetHub.ConsumerFleetHub.Entities;

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
@Table(name="Consumer")
public class Consumer 
{
	@Id
	@Column
	String id;
	@Column(unique=true)
	@NotNull
	String username;
	@NotNull
	@Column
	String password;
	@OneToOne(cascade=CascadeType.ALL)
	@JsonManagedReference("consumer-query")
	LoadQuery query;
	

}
