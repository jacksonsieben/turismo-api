package br.edu.utfpr.turismoapi.turismoapi.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.turismoapi.turismoapi.dto.PersonDTO;
import br.edu.utfpr.turismoapi.turismoapi.models.Person;
import br.edu.utfpr.turismoapi.turismoapi.repositories.PersonRepository;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/Person")
@CrossOrigin("*")
public class PersonController {
    @Autowired
    PersonRepository personRepository;

    @GetMapping("/pages")
    public ResponseEntity<Page<Person>> getAllPage(
        @PageableDefault(page=0, size=10, sort="nome",
            direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok()
            .body( personRepository.findAll(pageable) );
    }

    @GetMapping(value = {"", "/"})
    public List<Person> getAll() {
        return personRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable String id) {
        Optional<Person> personOpt = personRepository
            .findById(UUID.fromString(id));

        return personOpt.isPresent() 
            ? ResponseEntity.ok(personOpt.get())
            : ResponseEntity.notFound().build();
    }

    @PostMapping("")
    public ResponseEntity<Object> create(@Valid @RequestBody PersonDTO personDTO) {
        var pes = new Person(); 
        BeanUtils.copyProperties(personDTO, pes);

        try {
            return ResponseEntity
            .status(HttpStatus.CREATED)
            .body( personRepository.save(pes) );
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Falha ao criar pessoa");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable String id, 
                    @Valid @RequestBody PersonDTO personDTO) {
        UUID uuid;
        try { uuid = UUID.fromString(id); }
        catch(Exception e) {
            return ResponseEntity
                .badRequest()
                .body("Formato de UUID inválido");
        }

        var person = personRepository.findById(uuid);

        if(person.isEmpty())
            return ResponseEntity.notFound().build();

        var personToUpdate = person.get();
        BeanUtils.copyProperties(personDTO, personToUpdate);
        personToUpdate.setUpdatedAt(LocalDateTime.now());

        try {
            return ResponseEntity.ok()
            .body( personRepository.save(personToUpdate));
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Falha ao atualizar pessoa");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        UUID uuid;
        try { 
            uuid = UUID.fromString(id); 
        }
        catch(Exception e) {
            return ResponseEntity
                .badRequest()
                .body("Formato de UUID inválido");
        }

        var person = personRepository.findById(uuid);

        if(person.isEmpty())
            return ResponseEntity.notFound().build();

        try {
            personRepository.delete(person.get());
            return ResponseEntity.ok().build();
        } catch(Exception e) {
            e.printStackTrace();

            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}