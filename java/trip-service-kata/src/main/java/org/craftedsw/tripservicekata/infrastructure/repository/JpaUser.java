package org.craftedsw.tripservicekata.infrastructure.repository;

import org.craftedsw.tripservicekata.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class JpaUser {

	@Id
	private Integer id;

	@ManyToMany(mappedBy = "trips")
	private final List<JpaTrip> jpaTrips = new ArrayList<JpaTrip>();

	@ManyToMany(mappedBy = "frends")
	private final List<JpaUser> friends = new ArrayList<JpaUser>();

	@Column
	private String name;

	public JpaUser() {
	}

	public JpaUser(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public void addFriend(JpaUser jpaUser) {
		friends.add(jpaUser);
	}

	public void addTrip(JpaTrip jpaTrip) {
		jpaTrips.add(jpaTrip);
	}

	public List<JpaUser> getFriends() {
		return friends;
	}

	public List<JpaTrip> getTrips() {
		return jpaTrips;
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
		JpaUser jpaUser = (JpaUser) o;
		return Objects.equals(id, jpaUser.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public User convert() {
		return new User(id,
				name,
				friends.stream().map(JpaUser::convert).collect(Collectors.toList()),
				jpaTrips.stream().map(JpaTrip::convert).collect(Collectors.toList()));
	}
}
