package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.domain.TripRepository;
import org.craftedsw.tripservicekata.domain.User;
import org.craftedsw.tripservicekata.domain.Email;
import org.craftedsw.tripservicekata.infrastructure.email.EmailService;
import org.craftedsw.tripservicekata.infrastructure.repository.JpaTrip;
import org.craftedsw.tripservicekata.domain.UserSessionProvider;
import org.craftedsw.tripservicekata.infrastructure.http.TripController;
import org.craftedsw.tripservicekata.infrastructure.repository.JpaUser;
import org.craftedsw.tripservicekata.infrastructure.repository.SpringJpaTripRepository;
import org.craftedsw.tripservicekata.infrastructure.repository.TripDAO;
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
    private TripDAO tripDao;
    private UserSessionProvider userSessionProvider;
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        tripDao = mock(TripDAO.class);
        tripRepository = new SpringJpaTripRepository(tripDao);
        userSessionProvider = mock(UserSessionProvider.class);
        emailService = mock(EmailService.class);
        tripController = new TripController(tripRepository, userSessionProvider, emailService);
    }

    @Nested
    class GetTripsPriceByUser {
        @Test
        void should_return_internal_server_error_when_loggedUser_is_not_defined() {
            // given
            when(userSessionProvider.getLoggedUser()).thenReturn(null);
            JpaUser jpaUser = new JpaUser(42, "toto");

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
            void should_return_internal_200_with_0() {
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
            private List<JpaTrip> jpaTrips;

            @BeforeEach
            void setUp() {
                final JpaUser loggedJpaUser = new JpaUser();
                when(userSessionProvider.getLoggedUser()).thenReturn(loggedJpaUser.convert());
                jpaUser = new JpaUser(42, "toto");

                final JpaUser otherFriend = new JpaUser();
                this.jpaUser.addFriend(otherFriend);
                this.jpaUser.addFriend(loggedJpaUser);

                jpaTrips = asList(new JpaTrip(10f), new JpaTrip(15f));
                when(tripDao.findTripsByUserId(42)).thenReturn(jpaTrips);
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