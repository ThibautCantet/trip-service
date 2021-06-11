package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.domain.User;
import org.craftedsw.tripservicekata.domain.UserSessionProvider;
import org.craftedsw.tripservicekata.infrastructure.Trip;
import org.craftedsw.tripservicekata.infrastructure.email.Email;
import org.craftedsw.tripservicekata.infrastructure.email.EmailService;
import org.craftedsw.tripservicekata.infrastructure.TripController;
import org.craftedsw.tripservicekata.infrastructure.TripDAO;
import org.craftedsw.tripservicekata.infrastructure.JpaUser;
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
    private TripDAO tripDao;
    private UserSessionProvider userSessionProvider;
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        tripDao = mock(TripDAO.class);
        userSessionProvider = mock(UserSessionProvider.class);
        emailService = mock(EmailService.class);
        tripController = new TripController(tripDao, userSessionProvider, emailService);
    }

    @Nested
    class GetTripsPriceByUser {
        @Test
        void should_return_internal_server_error_when_loggedUser_is_not_defined() {
            // given
            when(userSessionProvider.getLoggedUser()).thenReturn(null);
            JpaUser jpaUser = new JpaUser();

            // when
            final ResponseEntity<Float> tripsPriceByUser = tripController.getTripsPriceByUser(jpaUser);


            // then
            assertThat(tripsPriceByUser.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @Nested
        class WhenLoggedUserIsDefinedAndLoggedUserIsNotAFriend {

            private JpaUser jpaUser;

            @BeforeEach
            void setUp() {
                User loggedUser = new User();
                when(userSessionProvider.getLoggedUser()).thenReturn(loggedUser);
                jpaUser = new JpaUser();
            }

            @Test
            void should_return_internal_200_with_empty_list_of_trip() {
                // when
                final ResponseEntity<Float> tripsPriceByUser = tripController.getTripsPriceByUser(jpaUser);

                // then
                assertThat(tripsPriceByUser.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(tripsPriceByUser.getBody()).isEqualTo(0);
            }

            @Test
            void should_return_not_sent_email() {
                // when
                tripController.getTripsPriceByUser(jpaUser);

                // then
                verify(emailService, never()).send(any());
            }
        }

        @Nested
        class WhenLoggedUserIsDefinedAndLoggedUserIsAFriend {

            private JpaUser jpaUser;
            private List<Trip> trips;

            @BeforeEach
            void setUp() {
                final User loggedUser = new User(1, "logged");
                when(userSessionProvider.getLoggedUser()).thenReturn(loggedUser);
                jpaUser = new JpaUser(42, "toto");

                final JpaUser otherFriend = new JpaUser();
                this.jpaUser.addFriend(otherFriend);
                this.jpaUser.addFriend(new JpaUser(loggedUser));

                trips = asList(new Trip(10f), new Trip(15f));
                when(tripDao.findTripsByUserId(42)).thenReturn(trips);
            }

            @Test
            void should_return_internal_200_with_sum_of_trips_price() {
                // when
                final ResponseEntity<Float> tripsPriceByUser = tripController.getTripsPriceByUser(jpaUser);

                // then
                assertThat(tripsPriceByUser.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(tripsPriceByUser.getBody()).isEqualTo(25f);
            }

            @Test
            void should_return_send_an_email_with_the_user_name_and_trips_count() {
                // when
                tripController.getTripsPriceByUser(jpaUser);

                // then
                final Email expectedSentEmail = new Email("toto", 2);
                ArgumentCaptor<Email> emailArgumentCaptor = ArgumentCaptor.forClass(Email.class);
                verify(emailService).send(emailArgumentCaptor.capture());
                assertThat(emailArgumentCaptor.getValue()).isEqualToComparingFieldByField(expectedSentEmail);
            }
        }
    }
}