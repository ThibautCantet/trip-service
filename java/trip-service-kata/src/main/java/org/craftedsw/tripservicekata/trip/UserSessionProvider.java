package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.user.User;

public interface UserSessionProvider {
    User getLoggedUser();
}
