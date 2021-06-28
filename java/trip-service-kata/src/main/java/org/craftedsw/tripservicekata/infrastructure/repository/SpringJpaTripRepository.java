package org.craftedsw.tripservicekata.infrastructure.repository;

import org.craftedsw.tripservicekata.domain.Trip;
import org.craftedsw.tripservicekata.domain.TripRepository;

import java.util.List;
import java.util.stream.Collectors;

public class SpringJpaTripRepository implements TripRepository {

    private final TripDAO tripDAO;

    public SpringJpaTripRepository(TripDAO tripDAO) {
        this.tripDAO = tripDAO;
    }

    @Override
    public List<Trip> findByUserId(int userId) {
        final List<JpaTrip> tripsByUserId = tripDAO.findTripsByUserId(userId);
        return tripsByUserId.stream()
                .map(JpaTrip::convert)
                .collect(Collectors.toList());
    }
}
