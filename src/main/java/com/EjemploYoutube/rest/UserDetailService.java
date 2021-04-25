package com.EjemploYoutube.rest;

import com.EjemploYoutube.dao.UsuarioDAO;
import com.EjemploYoutube.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;


@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UsuarioDAO usuarioDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByEmail(email);

        if(usuario == null){
            throw new UsernameNotFoundException("Not found");
        }

        return new User(usuario.getEmail(), usuario.getPassword(), getGrantedAuthority(usuario));
    }

    private Collection<GrantedAuthority> getGrantedAuthority (Usuario usuario) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        if(usuario.getRole().getName().equalsIgnoreCase("admin")){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        authorities.add(new SimpleGrantedAuthority("USER_USER"));

        return  authorities;
    }
}
