package org.craftedsw.tripservicekata.infrastructure.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TripDAO extends CrudRepository<JpaTrip, Integer> {

	List<JpaTrip> findTripsByUserId(int userId);
	
}