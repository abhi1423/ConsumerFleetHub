package com.abhinav.ConsumerFleetHub.Repositories;


import com.abhinav.ConsumerFleetHub.Entities.LoadQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadqueriesRepository extends JpaRepository<LoadQuery,String>
{
}
