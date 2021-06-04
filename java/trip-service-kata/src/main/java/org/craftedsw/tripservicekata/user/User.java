package org.craftedsw.tripservicekata.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.craftedsw.tripservicekata.trip.Trip;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class User {

	@Id
	private Integer id;

	@ManyToMany(mappedBy = "trips")
	private final List<Trip> trips = new ArrayList<Trip>();

	@ManyToMany(mappedBy = "frends")
	private final List<User> friends = new ArrayList<User>();

	@Column
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
}
