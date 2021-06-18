package org.craftedsw.tripservicekata.infrastructure.repository.jpa;

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
    private final List<JpaTrip> trips = new ArrayList<>();

    @ManyToMany(mappedBy = "frends")
    private final List<JpaUser> friends = new ArrayList<>();

    @Column
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JpaUser user = (JpaUser) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public User convert() {
        return new User(id,
                name,
                friends.stream()
                        .map(JpaUser::convert)
                        .collect(Collectors.toList()),
                trips.stream()
                        .map(JpaTrip::convert)
                        .collect(Collectors.toList()));
    }
}
