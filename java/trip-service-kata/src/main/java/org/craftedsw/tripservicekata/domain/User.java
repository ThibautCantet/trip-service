package org.craftedsw.tripservicekata.domain;

import org.craftedsw.tripservicekata.infrastructure.JpaUser;
import org.craftedsw.tripservicekata.infrastructure.Trip;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

	private Integer id;

	private List<Trip> trips = new ArrayList<Trip>();

	private List<User> friends = new ArrayList<User>();

	private String name;

	public User() {
	}

	public User(Integer id, String name, List<User> friends, List<Trip> trips) {
		this.id = id;
		this.name = name;
		this.friends = friends;
		this.trips = trips;
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
}
