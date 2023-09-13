package br.edu.utfpr.turismoapi.turismoapi.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.turismoapi.turismoapi.dto.PersonDTO;
import br.edu.utfpr.turismoapi.turismoapi.models.Person;
import br.edu.utfpr.turismoapi.turismoapi.repositories.PersonRepository;


@RestController
@RequestMapping("/Person")
public class PersonController {
    @Autowired
    PersonRepository personRepository;

    @GetMapping(value = {"", "/"})
    public List<Person> getAll(){
        List<Person> pessoas = personRepository.findAll();
        return pessoas;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable String id){
        Optional<Person> personOpt =personRepository.findById(UUID.fromString(id));
        return personOpt.isPresent() ? ResponseEntity.ok(personOpt.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping("")
    public ResponseEntity<Object> create(@RequestBody PersonDTO personDTO){
        Person pes = new Person();
        BeanUtils.copyProperties(personDTO, pes);

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(personRepository.save(pes));
        } catch (Exception e) {
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falhou ao criar pessoa");
        }
        
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody PersonDTO personDTO){
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Formato UUID invalido");
        }

        var person = personRepository.findById(uuid);

        if(person.isEmpty())
            return ResponseEntity.notFound().build();

        var personToUpdate = person.get();
        BeanUtils.copyProperties(personDTO, personToUpdate);
        personToUpdate.setUpdatedAt(LocalDateTime.now());

        try {
            return ResponseEntity.ok().body(personRepository.save(personToUpdate));
        } catch (Exception e) {
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falhou ao criar pessoa");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id){
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Formato UUID invalido");
        }

        var person = personRepository.findById(uuid);

        if(person.isEmpty())
            return ResponseEntity.notFound().build();

            try {
                personRepository.delete(person.get());
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
    }
}