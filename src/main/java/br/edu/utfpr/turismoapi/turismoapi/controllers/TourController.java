package br.edu.utfpr.turismoapi.turismoapi.controllers;

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

import br.edu.utfpr.turismoapi.turismoapi.dto.TourDTO;
import br.edu.utfpr.turismoapi.turismoapi.models.Person;
import br.edu.utfpr.turismoapi.turismoapi.models.Tour;
import br.edu.utfpr.turismoapi.turismoapi.repositories.PersonRepository;
import br.edu.utfpr.turismoapi.turismoapi.repositories.TourRepository;

@RestController
@RequestMapping("/tour")
public class TourController {
    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("")
    public List<Tour> getAll() {
        return tourRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<Tour> tourOpt = tourRepository.findById(id);
        return tourOpt.isPresent() ? ResponseEntity.ok(tourOpt.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping("")
    public ResponseEntity<Object> create(@RequestBody TourDTO tourDTO) {
        try {
            Tour tour = new Tour();
            BeanUtils.copyProperties(tourDTO, tour);
    
            // Busque e defina a agência com base no ID fornecido no DTO
            Optional<Person> agenciaOpt = personRepository.findById(UUID.fromString(tourDTO.getAgenciaId().toString()));
            if (agenciaOpt.isPresent()) {
                tour.setAgencia(agenciaOpt.get());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Agência não encontrada");
            }
    
            tourRepository.save(tour);
            return ResponseEntity.status(HttpStatus.CREATED).body(tour);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha ao criar passeio");
        }
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody TourDTO tourDTO) {
        Optional<Tour> tourOpt = tourRepository.findById(id);

        if (tourOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Tour tourToUpdate = tourOpt.get();
        BeanUtils.copyProperties(tourDTO, tourToUpdate);

        try {
            tourToUpdate.setId(id);
            tourRepository.save(tourToUpdate);
            return ResponseEntity.ok(tourToUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha ao atualizar passeio");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        Optional<Tour> tourOpt = tourRepository.findById(id);

        if (tourOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            tourRepository.delete(tourOpt.get());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
