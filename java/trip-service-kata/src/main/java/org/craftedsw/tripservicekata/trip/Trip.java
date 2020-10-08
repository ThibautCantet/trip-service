package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Trip {
    @Id
    private Integer id;

    @Column
    private User user;
}
