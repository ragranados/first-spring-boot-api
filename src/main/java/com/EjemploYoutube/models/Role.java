package com.EjemploYoutube.models;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role {
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String name;

    @OneToMany(targetEntity = Usuario.class, mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Usuario> users;

    public Role() { }

    public Role(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
