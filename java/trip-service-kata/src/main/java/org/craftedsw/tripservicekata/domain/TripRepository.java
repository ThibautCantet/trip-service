package org.craftedsw.tripservicekata.domain;

import java.util.List;

public interface TripRepository {
    List<Trip> findByUserId(UserId userId);
}
