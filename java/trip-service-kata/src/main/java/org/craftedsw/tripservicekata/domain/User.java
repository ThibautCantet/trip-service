package org.craftedsw.tripservicekata.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

	private Integer id;

	private final List<Trip> trips = new ArrayList<Trip>();

	private final List<User> friends = new ArrayList<User>();

	private String name;

	public User() {
	}

	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public void addFriend(User user) {
		friends.add(user);
	}

	public void addTrip(Trip trip) {
		trips.add(trip);
	}

	public List<User> getFriends() {
		return friends;
	}

	public List<Trip> getTrips() {
		return trips;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(id, user.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public boolean isFriendWith(User loggedUser) {
		for (User friend : this.getFriends()) {
			if (friend.equals(loggedUser)) {
				return true;
			}
		}

		return false;
	}
}
