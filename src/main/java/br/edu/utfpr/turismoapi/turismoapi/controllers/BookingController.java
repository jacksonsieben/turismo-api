package br.edu.utfpr.turismoapi.turismoapi.controllers;

import java.util.ArrayList;
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

import br.edu.utfpr.turismoapi.turismoapi.dto.BookingDTO;
import br.edu.utfpr.turismoapi.turismoapi.models.Booking;
import br.edu.utfpr.turismoapi.turismoapi.models.Person;
import br.edu.utfpr.turismoapi.turismoapi.models.Tour;
import br.edu.utfpr.turismoapi.turismoapi.repositories.BookingRepository;
import br.edu.utfpr.turismoapi.turismoapi.repositories.PersonRepository;
import br.edu.utfpr.turismoapi.turismoapi.repositories.TourRepository;


@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TourRepository tourRepository;

    @GetMapping("")
    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<Booking> bookingOpt = bookingRepository.findById(id);
        return bookingOpt.isPresent() ? ResponseEntity.ok(bookingOpt.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping("")
    public ResponseEntity<Object> create(@RequestBody BookingDTO bookingDTO) {
        try {
            Booking booking = new Booking();
            BeanUtils.copyProperties(bookingDTO, booking);
            Optional<Person> personOpt = personRepository.findById(UUID.fromString(bookingDTO.getClienteId().toString()));
            booking.setCliente(personOpt.get());
            Optional<Person> agenciaOpt = personRepository.findById(UUID.fromString(bookingDTO.getAgenciaId().toString()));
            booking.setAgencia(agenciaOpt.get());
            
            List<Tour> listPasseios = new ArrayList<>();
            for (UUID tourId : bookingDTO.getPasseiosIds()) {
                Optional<Tour> passeioOpt = tourRepository.findById(UUID.fromString(tourId.toString()));
                listPasseios.add(passeioOpt.get());
            }
            booking.setPasseios(listPasseios);
            bookingRepository.save(booking);
            return ResponseEntity.status(HttpStatus.CREATED).body(booking);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha ao criar reserva");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody BookingDTO bookingDTO) {
        Optional<Booking> bookingOpt = bookingRepository.findById(id);

        if (bookingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Booking bookingToUpdate = bookingOpt.get();
        BeanUtils.copyProperties(bookingDTO, bookingToUpdate);

        try {
            bookingToUpdate.setId(id);
            bookingRepository.save(bookingToUpdate);
            return ResponseEntity.ok(bookingToUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha ao atualizar reserva");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        Optional<Booking> bookingOpt = bookingRepository.findById(id);

        if (bookingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            bookingRepository.delete(bookingOpt.get());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}