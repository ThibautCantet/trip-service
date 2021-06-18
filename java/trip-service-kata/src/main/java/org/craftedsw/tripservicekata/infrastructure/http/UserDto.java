package org.craftedsw.tripservicekata.infrastructure.http;

import org.craftedsw.tripservicekata.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDto {

    private Integer id;

    private List<TripDto> trips = new ArrayList<>();

    private List<UserDto> friends = new ArrayList<>();

    private String name;

    public UserDto() {
    }

    public UserDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }

    public void addFriend(UserDto user) {
        friends.add(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto user = (UserDto) o;
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
                        .map(UserDto::convert)
                        .collect(Collectors.toList()),
                trips.stream()
                        .map(TripDto::convert)
                        .collect(Collectors.toList()));
    }
}
