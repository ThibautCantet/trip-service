package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.email.Email;
import org.craftedsw.tripservicekata.email.EmailService;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
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
    class GetTripByUser {
        @Test
        void should_return_internal_server_error_when_loggedUser_is_not_defined() {
            // given
            when(userSessionProvider.getLoggedUser()).thenReturn(null);
            User user = new User();

            // when
            final ResponseEntity<List<Trip>> tripsByUser = tripController.getTripsByUser(user);


            // then
            assertThat(tripsByUser.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @Nested
        class WhenLoggedUserIsDefinedAndLoggedUserIsNotAFriend {

            private User user;

            @BeforeEach
            void setUp() {
                User loggedUser = new User();
                when(userSessionProvider.getLoggedUser()).thenReturn(loggedUser);
                user = new User();
            }

            @Test
            void should_return_internal_200_with_empty_list_of_trip() {
                // when
                final ResponseEntity<List<Trip>> tripsByUser = tripController.getTripsByUser(user);

                // then
                assertThat(tripsByUser.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(tripsByUser.getBody()).isEqualTo(emptyList());
            }

            @Test
            void should_return_not_sent_email() {
                // when
                tripController.getTripsByUser(user);

                // then
                verify(emailService, never()).send(any());
            }
        }

        @Nested
        class WhenLoggedUserIsDefinedAndLoggedUserIsAFriend {

            private User user;
            private List<Trip> trips;

            @BeforeEach
            void setUp() {
                final User loggedUser = new User();
                when(userSessionProvider.getLoggedUser()).thenReturn(loggedUser);
                user = new User("toto");

                final User otherFriend = new User();
                this.user.addFriend(otherFriend);
                this.user.addFriend(loggedUser);

                trips = singletonList(new Trip());
                when(tripDao.findTripsByUser(this.user)).thenReturn(trips);
            }

            @Test
            void should_return_internal_200_with_list_of_trips() {
                // when
                final ResponseEntity<List<Trip>> tripsByUser = tripController.getTripsByUser(user);

                // then
                assertThat(tripsByUser.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(tripsByUser.getBody()).isEqualTo(trips);
            }

            @Test
            void should_return_send_an_email_with_the_user_name_and_trips_count() {
                // when
                tripController.getTripsByUser(user);

                // then
                final Email expectedSentEmail = new Email("toto", 1);
                ArgumentCaptor<Email> emailArgumentCaptor = ArgumentCaptor.forClass(Email.class);
                verify(emailService).send(emailArgumentCaptor.capture());
                assertThat(emailArgumentCaptor.getValue()).isEqualToComparingFieldByField(expectedSentEmail);
            }
        }
    }
}