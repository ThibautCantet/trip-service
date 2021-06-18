package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.domain.Trip;
import org.craftedsw.tripservicekata.domain.TripRepository;
import org.craftedsw.tripservicekata.domain.User;
import org.craftedsw.tripservicekata.domain.UserSessionProvider;
import org.craftedsw.tripservicekata.domain.Email;
import org.craftedsw.tripservicekata.infrastructure.email.CustomEmailService;
import org.craftedsw.tripservicekata.infrastructure.http.TripController;
import org.craftedsw.tripservicekata.infrastructure.http.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TripControllerTest {

    private TripController tripController;
    private TripRepository tripRepository;
    private UserSessionProvider userSessionProvider;
    private CustomEmailService emailService;

    @BeforeEach
    void setUp() {
        tripRepository = mock(TripRepository.class);
        userSessionProvider = mock(UserSessionProvider.class);
        emailService = mock(CustomEmailService.class);
        tripController = new TripController(tripRepository, userSessionProvider, emailService);
    }

    @Nested
    class GetTripsPriceByUser {
        @Test
        void should_return_internal_server_error_when_loggedUser_is_not_defined() {
            // given
            when(userSessionProvider.getLoggedUser()).thenReturn(null);
            UserDto userDto = new UserDto();

            // when
            final ResponseEntity<Float> tripsPriceByUser = tripController.getTripsPriceByUser(userDto);


            // then
            assertThat(tripsPriceByUser.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @Nested
        class WhenLoggedUserIsDefinedAndLoggedUserIsNotAFriend {

            private UserDto userDto;

            @BeforeEach
            void setUp() {
                User loggedUser = new User();
                when(userSessionProvider.getLoggedUser()).thenReturn(loggedUser);
                userDto = new UserDto();
            }

            @Test
            void should_return_internal_200_with_empty_list_of_trip() {
                // when
                final ResponseEntity<Float> tripsPriceByUser = tripController.getTripsPriceByUser(userDto);

                // then
                assertThat(tripsPriceByUser.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(tripsPriceByUser.getBody()).isEqualTo(0);
            }

            @Test
            void should_return_not_sent_email() {
                // when
                tripController.getTripsPriceByUser(userDto);

                // then
                verify(emailService, never()).send(any());
            }
        }

        @Nested
        class WhenLoggedUserIsDefinedAndLoggedUserIsAFriend {

            private UserDto userDto;
            private List<Trip> trips;

            @BeforeEach
            void setUp() {
                final User loggedUser = new User(1, "logged");
                when(userSessionProvider.getLoggedUser()).thenReturn(loggedUser);
                userDto = new UserDto(42, "toto");

                final UserDto otherFriend = new UserDto();
                this.userDto.addFriend(otherFriend);
                this.userDto.addFriend(new UserDto(loggedUser));

                trips = asList(new Trip(10f), new Trip(15f));
                when(tripRepository.findTripsByUserId(42)).thenReturn(trips);
            }

            @Test
            void should_return_internal_200_with_sum_of_trips_price() {
                // when
                final ResponseEntity<Float> tripsPriceByUser = tripController.getTripsPriceByUser(userDto);

                // then
                assertThat(tripsPriceByUser.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(tripsPriceByUser.getBody()).isEqualTo(25f);
            }

            @Test
            void should_return_send_an_email_with_the_user_name_and_trips_count() {
                // when
                tripController.getTripsPriceByUser(userDto);

                // then
                final Email expectedSentEmail = new Email("toto", 2);
                ArgumentCaptor<Email> emailArgumentCaptor = ArgumentCaptor.forClass(Email.class);
                verify(emailService).send(emailArgumentCaptor.capture());
                assertThat(emailArgumentCaptor.getValue()).isEqualToComparingFieldByField(expectedSentEmail);
            }
        }
    }
}