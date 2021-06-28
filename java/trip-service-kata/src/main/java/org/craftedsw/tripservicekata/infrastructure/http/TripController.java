package org.craftedsw.tripservicekata.infrastructure.http;

import org.craftedsw.tripservicekata.domain.TripRepository;
import org.craftedsw.tripservicekata.infrastructure.email.EmailService;
import org.craftedsw.tripservicekata.infrastructure.repository.JpaUser;
import org.craftedsw.tripservicekata.use_case.GetTripsPriceByUser;
import org.craftedsw.tripservicekata.domain.UserSessionProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TripController {

    private final GetTripsPriceByUser getTripsPriceByUser;

    public TripController(TripRepository tripRepository, UserSessionProvider userSessionProvider, EmailService emailService) {
        getTripsPriceByUser = new GetTripsPriceByUser(tripRepository, userSessionProvider, emailService);
    }

    @GetMapping("/api/trip/user/")
    public ResponseEntity<Float> getTripsPriceByUser(@RequestBody JpaUser jpaUser) {
        Float tripsPrice = getTripsPriceByUser.execute(jpaUser.convert());
        if (tripsPrice != null) {
            return new ResponseEntity<>(tripsPrice, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
