package com.EjemploYoutube.rest;

import com.EjemploYoutube.dao.RoleDAO;
import com.EjemploYoutube.dao.UsuarioDAO;
import com.EjemploYoutube.models.Role;
import com.EjemploYoutube.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("roles")
public class RoleRest {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private RoleDAO roleDao;

    @PostMapping("/guardar")
    public void guardar(@RequestBody Role role){
        roleDao.save(role);
    }

    @GetMapping("/listar")
    public List<Role> listar(){
        return roleDao.findAll();
    }

    @GetMapping("/rol")
    public ResponseEntity rol (){
        Optional<Role> rol = roleDao.findById(1);
        //Set<Usuario> lista = rol.get().getUsers();

        //List<Usuario> lista = usuarioDAO.findAll();

        return ResponseEntity.ok(rol);
    }
}
