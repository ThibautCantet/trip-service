package org.craftedsw.tripservicekata.use_case;

import org.craftedsw.tripservicekata.domain.*;

import java.util.List;
import java.util.Optional;

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

    public Optional<Float> execute(User user) {
        User loggedUser = userSessionProvider.getLoggedUser();
        if (loggedUser != null) {
            if (user.isFriendWith(loggedUser)) {
                List<Trip> tripList = tripRepository.findTripsByUserId(user.getId());

                final Trips trips = new Trips(tripList);
                final Float tripsPrice = trips.calculatePrice();

                emailService.send(new Email(user.getName(), tripList.size()));

                return Optional.ofNullable(tripsPrice);
            }
            return Optional.of(0f);

        }
        return Optional.empty();
    }
}
