package br.edu.utfpr.turismoapi.turismoapi.controllers;

import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.turismoapi.turismoapi.models.Person;
import br.edu.utfpr.turismoapi.turismoapi.repositories.PersonRepository;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/pessoa")
public class PersonController {
    @Autowired
    // PersonRepository personRepository;

    @GetMapping("")
    public String getAll(){
        return "All";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id){
        return "ById: " + id;
    }

    @PostMapping("")
    public String create(@RequestBody String person){
        // var pes = new Person();
        // pes.setNome("Jackson Sieben");
        // pes.setEmail("jack@email.com");
        // pes.setNascimento(LocalDateTime.now());

        // personRepository.save(pes);
        // return pes.toString();
        return "Created";
    }
    
    @PutMapping("/{id}")
    public String update(@RequestBody Long id){
        return "Updated: " + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        return "Deleted: " + id;
    }
}
