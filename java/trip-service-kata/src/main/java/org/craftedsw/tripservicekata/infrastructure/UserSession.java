package org.craftedsw.tripservicekata.infrastructure;

import org.craftedsw.tripservicekata.use_case.UserSessionProvider;

public class UserSession implements UserSessionProvider {

	private static final UserSession userSession = new UserSession();
	
	private UserSession() {
	}
	
	public static UserSession getInstance() {
		return userSession;
	}

	@Override
	public JpaUser getLoggedUser() {
		throw new CollaboratorCallException(
				"UserSession.getLoggedUser() should not be called in an unit test");
	}

}
