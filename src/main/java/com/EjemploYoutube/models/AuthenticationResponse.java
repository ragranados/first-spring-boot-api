package com.EjemploYoutube.models;

public class AuthenticationResponse {

    private final String jwt;
    //private final Usuario usuario;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
        //this.usuario = usuario;
    }

    public String getJwt() {
        return jwt;
    }
}
