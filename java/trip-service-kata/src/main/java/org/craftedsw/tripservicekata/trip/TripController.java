package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.email.Email;
import org.craftedsw.tripservicekata.email.EmailService;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;
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

    public TripController(TripDAO tripDAO) {
        this.tripDAO = tripDAO;
    }

    @GetMapping("/api/trip/user/")
    public ResponseEntity<List<Trip>> getTripsByUser(@RequestBody User user) {
        List<Trip> tripList = new ArrayList<Trip>();
        User loggedUser = UserSession.getInstance().getLoggedUser();
        boolean isFriend = false;
        if (loggedUser != null) {
            for (User friend : user.getFriends()) {
                if (friend.equals(loggedUser)) {
                    isFriend = true;
                    break;
                }
            }
            if (isFriend) {
                tripList = tripDAO.findTripsByUser(user);
            }
            final EmailService emailService = new EmailService();
            emailService.send(new Email(user.getName(), tripList.size()));
            return new ResponseEntity<>(tripList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
