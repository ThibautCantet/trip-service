package org.craftedsw.tripservicekata.infrastructure.repository.jpa;

import org.craftedsw.tripservicekata.domain.Trip;
import org.craftedsw.tripservicekata.domain.TripRepository;

import java.util.List;
import java.util.stream.Collectors;

public class JpaTripRepository implements TripRepository {
    private final JpaTripDAO jpaTripDAO;

    public JpaTripRepository(JpaTripDAO jpaTripDAO) {
        this.jpaTripDAO = jpaTripDAO;
    }

    @Override
    public List<Trip> findTripsByUserId(int userId) {
        return jpaTripDAO.findTripsByUserId(userId)
                .stream()
                .map(JpaTrip::convert)
                .collect(Collectors.toList());
    }
}
