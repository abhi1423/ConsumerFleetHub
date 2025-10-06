package com.ConsumerFleetHub.ConsumerFleetHub.Repositories;

import com.ConsumerFleetHub.ConsumerFleetHub.Entities.RequestToTransporter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<RequestToTransporter,Long>
{
}
