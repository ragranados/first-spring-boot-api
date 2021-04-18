package com.EjemploYoutube.rest;

import com.EjemploYoutube.dao.RoleDAO;
import com.EjemploYoutube.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("roles")
public class RoleRest {

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
}
