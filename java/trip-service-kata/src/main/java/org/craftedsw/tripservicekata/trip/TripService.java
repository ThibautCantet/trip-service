package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.email.Email;
import org.craftedsw.tripservicekata.email.EmailService;
import org.craftedsw.tripservicekata.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TripService {
    private final TripDAO tripDAO;
    private final UserSessionProvider userSessionProvider;
    private final EmailService emailService;

    public TripService(TripDAO tripDAO, UserSessionProvider userSessionProvider, EmailService emailService) {
        this.tripDAO = tripDAO;
        this.userSessionProvider = userSessionProvider;
        this.emailService = emailService;
    }

    public List<Trip> getTripsByUser(User user) {
        List<Trip> tripList = null;
        User loggedUser = userSessionProvider.getLoggedUser();
        boolean isFriend = false;
        if (loggedUser != null) {
            tripList = new ArrayList<>();
            for (User friend : user.getFriends()) {
                if (friend.equals(loggedUser)) {
                    isFriend = true;
                    break;
                }
            }
            if (isFriend) {
                tripList = tripDAO.findTripsByUserId(user.getId());
                emailService.send(new Email(user.getName(), tripList.size()));
            }
        }
        return tripList;
    }
}
