package org.craftedsw.tripservicekata.trip;

import java.util.List;

import org.craftedsw.tripservicekata.exception.CollaboratorCallException;
import org.craftedsw.tripservicekata.user.User;
import org.springframework.data.repository.CrudRepository;

public interface TripDAO extends CrudRepository<Trip, Integer> {

	List<Trip> findTripsByUser(User user);
	
}