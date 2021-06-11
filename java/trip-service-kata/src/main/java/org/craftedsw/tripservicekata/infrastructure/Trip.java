package org.craftedsw.tripservicekata.infrastructure;

import org.craftedsw.tripservicekata.infrastructure.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Trip {
    @Id
    private Integer id;

    @Column
    private User user;

    @Column
    private Float price;

    public Trip(float price) {
        this.price = price;
    }

    public Float getPrice() {
        return price;
    }
}
