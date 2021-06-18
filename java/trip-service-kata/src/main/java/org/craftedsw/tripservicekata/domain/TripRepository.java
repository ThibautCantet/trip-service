package org.craftedsw.tripservicekata.domain;

import java.util.List;

public interface TripRepository {
    List<Trip> findTripsByUserId(int userId);
}