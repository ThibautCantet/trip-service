package org.craftedsw.tripservicekata.trip;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TripDAO extends CrudRepository<Trip, Integer> {

	List<Trip> findTripsByUserId(int userId);
	
}