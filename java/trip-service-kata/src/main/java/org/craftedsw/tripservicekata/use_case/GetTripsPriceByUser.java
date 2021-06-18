package org.craftedsw.tripservicekata.use_case;

import org.craftedsw.tripservicekata.domain.*;

import java.util.List;

public class GetTripsPriceByUser {

    private final UserSessionProvider userSessionProvider;
    private final TripRepository tripRepository;
    private final EmailService emailService;

    public GetTripsPriceByUser(UserSessionProvider userSessionProvider,
                               TripRepository jpaTripRepository,
                               EmailService emailService) {
        this.userSessionProvider = userSessionProvider;
        this.tripRepository = jpaTripRepository;
        this.emailService = emailService;
    }

    public Float execute(User user) {
        User loggedUser = userSessionProvider.getLoggedUser();
        if (loggedUser != null) {
            if (user.isFriendWith(loggedUser)) {
                List<Trip> tripList = tripRepository.findTripsByUserId(user.getId());

                final Trips trips = new Trips(tripList);
                final Float tripsPrice = trips.calculatePrice();

                emailService.send(new Email(user.getName(), tripList.size()));

                return tripsPrice;
            }
            return 0f;

        }
        return null;
    }
}
