package org.craftedsw.tripservicekata.infrastructure;

import org.craftedsw.tripservicekata.domain.CollaboratorCallException;
import org.craftedsw.tripservicekata.domain.User;
import org.craftedsw.tripservicekata.domain.UserSessionProvider;

public class UserSession implements UserSessionProvider {

	private static final UserSession userSession = new UserSession();
	
	private UserSession() {
	}
	
	public static UserSession getInstance() {
		return userSession;
	}

	@Override
	public User getLoggedUser() {
		throw new CollaboratorCallException(
				"UserSession.getLoggedUser() should not be called in an unit test");
	}

}
