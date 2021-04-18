package com.EjemploYoutube.dao;

import com.EjemploYoutube.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {

    @Query("FROM Usuario WHERE email=?1")
    Usuario findByEmail(@Param("email") String email);
}
