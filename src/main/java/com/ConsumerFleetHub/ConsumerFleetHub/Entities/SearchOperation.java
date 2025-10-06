package com.ConsumerFleetHub.ConsumerFleetHub.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="DummyUser")
public class SearchOperation 
{
	@Id
	@Column
	int id;
	@Column
	int id2;
	@Column
	String name1;
	@Column
	String name2;
	@Column
	String name3;

}
