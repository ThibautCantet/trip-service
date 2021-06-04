package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.email.EmailService;
import org.craftedsw.tripservicekata.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TripController {

    private final TripService tripService;

    public TripController(TripDAO tripDAO, UserSessionProvider userSessionProvider, EmailService emailService) {
        this.tripService = new TripService(tripDAO, userSessionProvider, emailService);
    }

    @GetMapping("/api/trip/user/")
    public ResponseEntity<List<Trip>> getTripsByUser(@RequestBody User user) {
        List<Trip> tripList = this.tripService.getTripsByUser(user);

        if (tripList == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(tripList, HttpStatus.OK);
        }
    }

}
