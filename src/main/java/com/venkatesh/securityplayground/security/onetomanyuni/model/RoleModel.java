package com.venkatesh.securityplayground.security.onetomanyuni.model;

import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class RoleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public RoleModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public RoleModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
