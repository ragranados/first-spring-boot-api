package com.EjemploYoutube.rest;

import com.EjemploYoutube.Mail.EmailServiceImpl;
import com.EjemploYoutube.dao.RoleDAO;
import com.EjemploYoutube.dao.UsuarioDAO;
import com.EjemploYoutube.dao.VerificationTokenDAO;
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
import java.util.UUID;

@RequestMapping("/auth")
@RestController
public class Auth {

    //@Autowired
    //private RoleDAO roleDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private VerificationTokenDAO verificationTokenDAO;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private EmailServiceImpl emailService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{

        Usuario usuario = usuarioDAO.findByEmail(authenticationRequest.getUsername());

        if(usuario == null){
            return ResponseEntity.status(400).body("Correo no encontrado");
        }

        if(!usuario.isEnabled()){
            return ResponseEntity.status(400).body("Debes activar tu cuenta, revisa tu correo.");
        }

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }catch (BadCredentialsException e){
            return ResponseEntity.status(400).body("Credenciales incorrectas");
            //throw new Exception("Bad credentials", e);
        }

        final UserDetails userDetails = userDetailService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario user) throws Exception{

        Usuario exist = usuarioDAO.findByEmail(user.getEmail());

        if(exist != null){
            return ResponseEntity.status(404).body("El correo ya se encuentra registrado");
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usuarioDAO.save(user);

        String newToken = java.util.UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(newToken, user);

        /*emailService.sendActivateAccountMail(
                user.getEmail(),
                "Verificacion Spring",
                "http://localhost:8080/auth/enable?token="+newToken
        );*/

        verificationTokenDAO.save(verificationToken);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/enable")
    public ResponseEntity<?> enableAccount(@RequestParam String token){

        VerificationToken exists = verificationTokenDAO.findByUUID(token);

        if(exists != null){
            Usuario usuario = usuarioDAO.findByEmail(exists.getUsuario().getEmail());

            usuario.setEnabled(true);

            usuarioDAO.save(usuario);

            verificationTokenDAO.delete(exists);
        }else{
            return ResponseEntity.status(201).body("La cuenta ya ha sido activada!");
        }

        return ResponseEntity.ok(exists);
    }
}
