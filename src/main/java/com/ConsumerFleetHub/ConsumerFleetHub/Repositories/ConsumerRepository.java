package com.ConsumerFleetHub.ConsumerFleetHub.Repositories;


import com.ConsumerFleetHub.ConsumerFleetHub.Entities.Consumer;
import com.ConsumerFleetHub.ConsumerFleetHub.Entities.LoadQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer,String>
{
	Optional<Consumer> findByUsername(String username);
	@Query(value = "SELECT lq.* FROM consumer c JOIN load_queries lq ON c.query_id = lq.id WHERE lq.is_resolved = false", nativeQuery = true)
	List<LoadQuery> findUnresolvedQueries();
	@Query(value="select c.* from consumer c JOIN load_queries lq on c.query_id=lq.id where lq.is_resolved = false",nativeQuery = true)
	List<Consumer> findUsersWithPendingQueries();
}
