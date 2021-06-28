package org.craftedsw.tripservicekata.infrastructure;

import org.craftedsw.tripservicekata.use_case.TripService;
import org.craftedsw.tripservicekata.use_case.UserSessionProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TripController {

    private final TripService tripService;

    public TripController(TripDAO tripDAO, UserSessionProvider userSessionProvider, EmailService emailService) {
        tripService = new TripService(tripDAO, userSessionProvider, emailService);
    }

    @GetMapping("/api/trip/user/")
    public ResponseEntity<Float> getTripsPriceByUser(@RequestBody JpaUser jpaUser) {
        Float tripsPrice = tripService.getTripsPriceByUser(jpaUser);
        if (tripsPrice != null) {
            return new ResponseEntity<>(tripsPrice, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
