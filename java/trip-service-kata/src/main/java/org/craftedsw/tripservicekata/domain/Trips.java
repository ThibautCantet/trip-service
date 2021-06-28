package org.craftedsw.tripservicekata.domain;


import java.util.List;

public class Trips {

    private final List<Trip> trips;

    public Trips(List<Trip> trips) {
        this.trips = trips;
    }

    public Float computePrices() {
        return trips.stream()
                .map(Trip::getPrice)
                .reduce(Float::sum)
                .orElse(0f);
    }
}
