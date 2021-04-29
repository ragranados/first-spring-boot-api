package com.EjemploYoutube.models;

import com.fasterxml.jackson.annotation.*;
import lombok.Builder;

import javax.persistence.*;

/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")*/
@Entity
public class Usuario {
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    @JsonIgnore
    private String password;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

    @Column(name = "enabled", columnDefinition = "boolean default false")
    private boolean enabled;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
