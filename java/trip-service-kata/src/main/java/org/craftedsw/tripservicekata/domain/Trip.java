package org.craftedsw.tripservicekata.domain;


public class Trip {
    private Integer id;

    private User user;

    private Float price;

    public Trip(float price) {
        this.price = price;
    }

    public Trip(Integer id, Float price, User user) {
        this.id = id;
        this.price = price;
        this.user = user;
    }

    public Float getPrice() {
        return price;
    }

}
