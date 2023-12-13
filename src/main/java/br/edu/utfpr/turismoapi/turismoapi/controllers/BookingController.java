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
import jakarta.validation.Valid;

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

    // @PostMapping("")
    // public ResponseEntity<Object> create(@Valid @RequestBody BookingDTO
    // bookingDTO) {
    // try {
    // Booking booking = new Booking();
    // BeanUtils.copyProperties(bookingDTO, booking);
    // Optional<Person> personOpt =
    // personRepository.findById(UUID.fromString(bookingDTO.getClienteId().toString()));
    // booking.setCliente(personOpt.get());
    // Optional<Person> agenciaOpt =
    // personRepository.findById(UUID.fromString(bookingDTO.getAgenciaId().toString()));
    // booking.setAgencia(agenciaOpt.get());

    // List<Tour> listPasseios = new ArrayList<>();
    // for (UUID tourId : bookingDTO.getPasseiosIds()) {
    // Optional<Tour> passeioOpt =
    // tourRepository.findById(UUID.fromString(tourId.toString()));
    // listPasseios.add(passeioOpt.get());
    // }
    // booking.setPasseios(listPasseios);
    // bookingRepository.save(booking);
    // return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    // } catch (Exception e) {
    // e.printStackTrace();
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha ao criar
    // reserva");
    // }
    // }
    @PostMapping("")
    public ResponseEntity<Object> create(@Valid @RequestBody BookingDTO bookingDTO) {
        try {
            // Cria uma nova instância da entidade Booking
            Booking booking = new Booking();

            // Imprime o DTO recebido para verificar as datas
            System.out.println("DTO Recebido: " + bookingDTO);

            // Copia as propriedades do DTO para a entidade Booking
            BeanUtils.copyProperties(bookingDTO, booking);

            // Imprime a data antes de definir na entidade Booking
            System.out.println("Data Inicial antes de definir: " + booking.getDataInicial());
            System.out.println("Data Final antes de definir: " + booking.getDataFinal());

            // Busca a agência no repositório usando o ID fornecido no DTO
            Optional<Person> agenciaOpt = personRepository
                    .findById(UUID.fromString(bookingDTO.getAgenciaId().toString()));

            // Verifica se a agência foi encontrada
            if (agenciaOpt.isPresent()) {
                // Define a agência na reserva
                booking.setAgencia(agenciaOpt.get());

                // Inicializa uma lista para armazenar os passeios
                List<Tour> listPasseios = new ArrayList<>();

                // Itera sobre os IDs de passeios fornecidos no DTO
                for (String tourId : bookingDTO.getPasseiosIds()) {
                    // Busca o passeio no repositório usando o ID do passeio
                    Optional<Tour> passeioOpt = tourRepository.findById(UUID.fromString(tourId));

                    // Verifica se o passeio foi encontrado
                    passeioOpt.ifPresent(listPasseios::add);
                }

                // Define a lista de passeios na reserva
                booking.setPasseios(listPasseios);

                // Imprime a data depois de definir na entidade Booking
                System.out.println("Data Inicial depois de definir: " + booking.getDataInicial());
                System.out.println("Data Final depois de definir: " + booking.getDataFinal());

                // Salva a reserva no repositório
                bookingRepository.save(booking);

                // Retorna uma resposta de sucesso com a reserva criada
                return ResponseEntity.status(HttpStatus.CREATED).body(booking);
            } else {
                // Retorna uma resposta de erro se a agência não for encontrada
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Agência não encontrada");
            }
        } catch (Exception e) {
            // Em caso de falha, imprime o stack trace da exceção e retorna uma resposta de
            // erro
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha ao criar reserva");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @Valid @RequestBody BookingDTO bookingDTO) {
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

    @PostMapping("/{bookingId}/addUser/{userId}")
    public ResponseEntity<Object> addUserToBooking(@PathVariable UUID bookingId, @PathVariable UUID userId) {
        // Verifica se a reserva existe
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (bookingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Verifica se o usuário existe
        Optional<Person> userOpt = personRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não encontrado");
        }

        // Adiciona o usuário à lista de clientes da reserva
        Booking booking = bookingOpt.get();
        List<Person> clients = booking.getClientes();
        if (clients == null) {
            clients = new ArrayList<>();
        }
        clients.add(userOpt.get());
        booking.setClientes(clients);

        // Salva a reserva atualizada no repositório
        bookingRepository.save(booking);

        return ResponseEntity.ok(booking);
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<Object> getPersonBookings(@PathVariable UUID personId) {
        Optional<Person> personOpt = personRepository.findById(personId);

        if (personOpt.isPresent()) {
            List<Booking> bookings = personOpt.get().getReservas();
            return ResponseEntity.ok(bookings);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/agency/{agencyId}/packages-with-clients")
    public ResponseEntity<Object> getPackagesWithClients(@PathVariable UUID agencyId) {
        // Busca a agência no repositório usando o ID fornecido
        Optional<Person> agencyOpt = personRepository.findById(agencyId);

        // Verifica se a agência foi encontrada
        if (agencyOpt.isPresent()) {
            // Chama o método personalizado no repositório para obter pacotes associados à
            // agência
            List<Booking> packagesWithClients = bookingRepository.findPackagesByAgency(agencyOpt.get());

            // Retorna a lista de pacotes com clientes associados
            return ResponseEntity.ok(packagesWithClients);
        } else {
            // Retorna uma resposta de erro se a agência não for encontrada
            return ResponseEntity.notFound().build();
        }
    }

}