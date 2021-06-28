package org.craftedsw.tripservicekata.domain;

public class Trip {
    private Integer id;

    private User user;

    private Float price;

    public Trip(float price) {
        this.price = price;
    }

    public Float getPrice() {
        return price;
    }
}
