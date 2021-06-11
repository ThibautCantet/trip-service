package org.craftedsw.tripservicekata.domain;

import org.craftedsw.tripservicekata.infrastructure.User;

public interface UserSessionProvider {
    User getLoggedUser();
}
