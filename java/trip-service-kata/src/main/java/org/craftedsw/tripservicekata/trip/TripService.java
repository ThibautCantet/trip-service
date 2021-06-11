package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.email.Email;
import org.craftedsw.tripservicekata.email.EmailService;
import org.craftedsw.tripservicekata.user.User;

import java.util.ArrayList;
import java.util.List;

public class TripService {

    private final UserSessionProvider userSessionProvider;
    private final TripDAO tripDAO;
    private final EmailService emailService;

    public TripService(UserSessionProvider userSessionProvider, TripDAO tripDAO, EmailService emailService) {
        this.userSessionProvider = userSessionProvider;
        this.tripDAO = tripDAO;
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
                tripList = tripDAO.findTripsByUserId(user.getId());

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
