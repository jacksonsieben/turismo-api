package br.edu.utfpr.turismoapi.turismoapi.controllers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.turismoapi.turismoapi.dto.AuthDTO;
import br.edu.utfpr.turismoapi.turismoapi.models.Person;
import br.edu.utfpr.turismoapi.turismoapi.repositories.PersonRepository;
import br.edu.utfpr.turismoapi.turismoapi.security.JwtUtil;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/Auth")
public class AuthController {
    
    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping(value = {""})
    public ResponseEntity<Object> auth(
        @Valid @RequestBody AuthDTO authDto){
        System.out.println(authDto);

        var payload = new HashMap<String, Object>();
        payload.put("username", authDto.username);

        var now = Instant.now();

        var jwt = jwtUtil.generateToken(payload, "12345", 3600);

        var res = new HashMap<String, Object>();
        res.put("token", jwt);
        res.put("generatedAt", now);
        res.put("expiresIn", now.plus(3600, ChronoUnit.SECONDS));

        return ResponseEntity.ok().body(res);
    }
    
}
