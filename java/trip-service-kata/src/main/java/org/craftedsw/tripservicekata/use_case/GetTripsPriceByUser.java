package org.craftedsw.tripservicekata.use_case;

import org.craftedsw.tripservicekata.domain.*;

import java.util.ArrayList;
import java.util.List;

public class GetTripsPriceByUser {

    private final TripRepository tripRepository;
    private final UserSessionProvider userSessionProvider;
    private final EmailSender emailService;

    public GetTripsPriceByUser(TripRepository tripRepository, UserSessionProvider userSessionProvider, EmailSender emailSender) {
        this.tripRepository = tripRepository;
        this.userSessionProvider = userSessionProvider;
        this.emailService = emailSender;
    }

    public Float execute(User user) {
        Float tripsPrice = null;
        List<Trip> jpaTripList = new ArrayList<Trip>();
        User loggedJpaUser = userSessionProvider.getLoggedUser();
        if (loggedJpaUser != null) {
            if (user.isFriendWith(loggedJpaUser)) {
                jpaTripList = tripRepository.findByUserId(user.getId());

                emailService.send(new Email(user.getName(), jpaTripList.size()));
            }
            final Trips trips = new Trips(jpaTripList);

            tripsPrice = trips.computePrices();
        }
        return tripsPrice;
    }
}
