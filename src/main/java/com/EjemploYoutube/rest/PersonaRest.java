package com.EjemploYoutube.rest;

import com.EjemploYoutube.dao.PersonaDAO;
import com.EjemploYoutube.models.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("personas")
public class PersonaRest {

    @Autowired
    private PersonaDAO personaDAO;

    @PostMapping("/guardar")
    public void guardar(@RequestBody Persona persona){

        personaDAO.save(persona);
    }

    @GetMapping("/listar")
    public List<Persona> listar(){
        return personaDAO.findAll();
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable("id") Integer id){
        personaDAO.deleteById(id);
    }

    @PutMapping("/actualizar")
    public void actualizar(@RequestBody Persona persona){
        personaDAO.save(persona);
    }
}
