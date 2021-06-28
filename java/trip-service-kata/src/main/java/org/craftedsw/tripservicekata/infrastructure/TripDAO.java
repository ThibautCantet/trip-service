package org.craftedsw.tripservicekata.infrastructure;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TripDAO extends CrudRepository<JpaTrip, Integer> {

	List<JpaTrip> findTripsByUserId(int userId);
	
}