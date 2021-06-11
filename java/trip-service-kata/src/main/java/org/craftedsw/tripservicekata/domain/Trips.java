package org.craftedsw.tripservicekata.domain;

import java.util.List;

public class Trips {

    private List<Trip> tripList;

    public Trips(List<Trip> tripList) {

        this.tripList = tripList;
    }

    public Float calculatePrice() {
        return tripList.stream()
                .map(Trip::getPrice)
                .reduce(Float::sum)
                .orElse(0f);
    }
}
