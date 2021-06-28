package org.craftedsw.tripservicekata.use_case;

import org.craftedsw.tripservicekata.infrastructure.JpaUser;

public interface UserSessionProvider {
    JpaUser getLoggedUser();
}
