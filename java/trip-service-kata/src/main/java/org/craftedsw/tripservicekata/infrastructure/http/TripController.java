package org.craftedsw.tripservicekata.infrastructure.http;

import org.craftedsw.tripservicekata.domain.TripRepository;
import org.craftedsw.tripservicekata.infrastructure.email.CustomEmailService;
import org.craftedsw.tripservicekata.use_case.GetTripsPriceByUser;
import org.craftedsw.tripservicekata.domain.UserSessionProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class TripController {

    private final GetTripsPriceByUser getTripsPriceByUser;

    public TripController(TripRepository tripRepository, UserSessionProvider userSessionProvider, CustomEmailService emailService) {
        this.getTripsPriceByUser = new GetTripsPriceByUser(userSessionProvider, tripRepository, emailService);
    }

    @GetMapping("/api/trip/user/")
    public ResponseEntity<Float> getTripsPriceByUser(@RequestBody UserDto userDto) {
        Optional<Float> tripsPrice = getTripsPriceByUser.execute(userDto.convert());

        if (tripsPrice.isPresent()) {
            return new ResponseEntity<>(tripsPrice.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}