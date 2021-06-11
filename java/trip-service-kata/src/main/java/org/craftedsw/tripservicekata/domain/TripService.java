package org.craftedsw.tripservicekata.domain;

import org.craftedsw.tripservicekata.infrastructure.email.Email;
import org.craftedsw.tripservicekata.infrastructure.email.EmailService;

import java.util.ArrayList;
import java.util.List;

public class TripService {

    private final UserSessionProvider userSessionProvider;
    private final TripRepository tripRepository;
    private final EmailService emailService;

    public TripService(UserSessionProvider userSessionProvider, TripRepository jpaTripRepository, EmailService emailService) {
        this.userSessionProvider = userSessionProvider;
        this.tripRepository = jpaTripRepository;
        this.emailService = emailService;
    }

    public Float getTripsPriceByUser(User user) {
        User loggedUser = userSessionProvider.getLoggedUser();
        boolean isFriend = false;
        if (loggedUser != null) {
            for (User friend : user.getFriends()) {
                if (friend.equals(loggedUser)) {
                    isFriend = true;
                    break;
                }
            }
            List<Trip> tripList = new ArrayList<>();
            if (isFriend) {
                tripList = tripRepository.findTripsByUserId(user.getId());

                emailService.send(new Email(user.getName(), tripList.size()));
            }
            return tripList.stream()
                    .map(Trip::getPrice)
                    .reduce(Float::sum)
                    .orElse(0f);

        }
        return null;
    }
}
