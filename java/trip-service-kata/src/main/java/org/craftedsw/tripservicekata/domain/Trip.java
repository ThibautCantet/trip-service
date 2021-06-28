package org.craftedsw.tripservicekata.domain;

import org.craftedsw.tripservicekata.infrastructure.JpaUser;

public class Trip {
    private Integer id;

    private JpaUser jpaUser;

    private Float price;

    public Trip(float price) {
        this.price = price;
    }

    public Float getPrice() {
        return price;
    }
}
