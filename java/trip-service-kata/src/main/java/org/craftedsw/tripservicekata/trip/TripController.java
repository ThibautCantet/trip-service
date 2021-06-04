package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.email.Email;
import org.craftedsw.tripservicekata.email.EmailService;
import org.craftedsw.tripservicekata.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TripController {

    private final TripDAO tripDAO;
    private final UserSessionProvider userSessionProvider;
    private final EmailService emailService;

    public TripController(TripDAO tripDAO, UserSessionProvider userSessionProvider, EmailService emailService) {
        this.tripDAO = tripDAO;
        this.userSessionProvider = userSessionProvider;
        this.emailService = emailService;
    }

    @GetMapping("/api/trip/user/")
    public ResponseEntity<Float> getTripsPriceByUser(@RequestBody User user) {
        List<Trip> tripList = new ArrayList<Trip>();
        User loggedUser = userSessionProvider.getLoggedUser();
        boolean isFriend = false;
        if (loggedUser != null) {
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
            Float tripsPrice = tripList.stream()
                    .map(Trip::getPrice)
                    .reduce(Float::sum)
                    .orElse(0f);
            return new ResponseEntity<>(tripsPrice, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
