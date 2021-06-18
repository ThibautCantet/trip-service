package org.craftedsw.tripservicekata.infrastructure.http;

import org.craftedsw.tripservicekata.domain.Trip;

public class TripDto {
    private Integer id;

    private UserDto user;

    private Float price;

    public TripDto(float price) {
        this.price = price;
    }

    public Float getPrice() {
        return price;
    }

    public Trip convert() {
        return new Trip(id, price, user.convert());
    }
}
