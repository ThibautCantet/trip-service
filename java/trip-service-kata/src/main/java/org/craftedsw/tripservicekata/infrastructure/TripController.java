package org.craftedsw.tripservicekata.infrastructure;

import org.craftedsw.tripservicekata.domain.TripRepository;
import org.craftedsw.tripservicekata.infrastructure.email.EmailService;
import org.craftedsw.tripservicekata.domain.TripService;
import org.craftedsw.tripservicekata.domain.UserSessionProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TripController {

    private final TripService tripService;

    public TripController(TripRepository tripRepository, UserSessionProvider userSessionProvider, EmailService emailService) {
        this.tripService = new TripService(userSessionProvider, tripRepository, emailService);
    }

    @GetMapping("/api/trip/user/")
    public ResponseEntity<Float> getTripsPriceByUser(@RequestBody JpaUser user) {
        Float tripsPrice = tripService.getTripsPriceByUser(user.convert());

        if (tripsPrice == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(tripsPrice, HttpStatus.OK);
        }
    }

}
