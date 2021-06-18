package org.craftedsw.tripservicekata.infrastructure.repository.jpa;

import org.craftedsw.tripservicekata.domain.Trip;
import org.craftedsw.tripservicekata.infrastructure.repository.jpa.JpaUser;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class JpaTrip {
    @Id
    private Integer id;

    @Column
    private JpaUser user;

    @Column
    private Float price;

    public JpaTrip(float price) {
        this.price = price;
    }

    public Float getPrice() {
        return price;
    }

    public Trip convert() {
        return new Trip(id, price, user.convert());
    }
}
