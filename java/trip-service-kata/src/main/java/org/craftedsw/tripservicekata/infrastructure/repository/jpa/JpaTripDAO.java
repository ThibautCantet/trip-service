package org.craftedsw.tripservicekata.infrastructure.repository.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface JpaTripDAO extends CrudRepository<JpaTrip, Integer> {

	List<JpaTrip> findTripsByUserId(int userId);
	
}