package com.EjemploYoutube.rest;

import com.EjemploYoutube.dao.UsuarioDAO;
import com.EjemploYoutube.models.Persona;
import com.EjemploYoutube.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usuarios")
public class UsuarioRest {

    @Autowired
    private UsuarioDAO usuarioDao;

    @GetMapping("/listar")
    public List<Usuario> listar(){
        return usuarioDao.findAll();
    }
}
