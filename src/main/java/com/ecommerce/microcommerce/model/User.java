package com.ecommerce.microcommerce.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @Column
    private String username;
    @Column
    @JsonIgnore
    private String password;

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}




