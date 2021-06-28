package org.craftedsw.tripservicekata.use_case;

import org.craftedsw.tripservicekata.domain.Trips;
import org.craftedsw.tripservicekata.infrastructure.EmailService;
import org.craftedsw.tripservicekata.infrastructure.JpaTrip;
import org.craftedsw.tripservicekata.infrastructure.TripDAO;
import org.craftedsw.tripservicekata.infrastructure.JpaUser;

import java.util.ArrayList;
import java.util.List;

public class TripService {


    private final TripDAO tripDAO;
    private final UserSessionProvider userSessionProvider;
    private final EmailService emailService;

    public TripService(TripDAO tripDAO, UserSessionProvider userSessionProvider, EmailService emailService) {
        this.tripDAO = tripDAO;
        this.userSessionProvider = userSessionProvider;
        this.emailService = emailService;
    }

    public Float getTripsPriceByUser(JpaUser jpaUser) {
        Float tripsPrice = null;
        List<JpaTrip> jpaTripList = new ArrayList<JpaTrip>();
        JpaUser loggedJpaUser = userSessionProvider.getLoggedUser();
        if (loggedJpaUser != null) {
            if (jpaUser.isFriendWith(loggedJpaUser)) {
                jpaTripList = tripDAO.findTripsByUserId(jpaUser.getId());

                emailService.send(new Email(jpaUser.getName(), jpaTripList.size()));
            }
            final Trips trips = new Trips(jpaTripList);

            tripsPrice = trips.computePrices();
        }
        return tripsPrice;
    }
}
