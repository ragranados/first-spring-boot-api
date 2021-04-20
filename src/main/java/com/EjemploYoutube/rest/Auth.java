package com.EjemploYoutube.rest;

import com.EjemploYoutube.dao.RoleDAO;
import com.EjemploYoutube.dao.UsuarioDAO;
import com.EjemploYoutube.models.*;
import com.EjemploYoutube.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;

@RestController
public class Auth {

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }catch (BadCredentialsException e){
            throw new Exception("Bad credentials", e);
        }

        final UserDetails userDetails = userDetailService.loadUserByUsername(authenticationRequest.getUsername());

        //System.out.println("Usuario: " + userDetails.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    /*@GetMapping("/register")
    public String showRegistration(WebRequest request, Model model){
        Usuario usuario = new Usuario();
        model.addAttribute("Usuario", usuario);
        return "registration";
    }*/

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario user) throws Exception{
        //Optional<Role> role = roleDAO.findById(user.getRole().getId());
        //user.setRole(role.get());
        //user.setRole(role);

        Usuario exist = usuarioDAO.findByEmail(user.getEmail());

        if(exist != null){
            return ResponseEntity.status(404).body("El correo ya se encuentra registrado");
        }

        //System.out.println(usuarioDAO.findById(7));
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usuarioDAO.save(user);

        return ResponseEntity.ok(user);
    }
}
