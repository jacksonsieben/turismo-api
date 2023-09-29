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

import br.edu.utfpr.turismoapi.turismoapi.dto.PaymentDTO;
import br.edu.utfpr.turismoapi.turismoapi.models.Booking;
import br.edu.utfpr.turismoapi.turismoapi.models.Payment;
import br.edu.utfpr.turismoapi.turismoapi.repositories.BookingRepository;
import br.edu.utfpr.turismoapi.turismoapi.repositories.PaymentRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentRepository paymentRepository;

    
    @Autowired
    private BookingRepository bookingRepository;
    
    @GetMapping("")
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<Payment> paymentOpt = paymentRepository.findById(id);
        return paymentOpt.isPresent() ? ResponseEntity.ok(paymentOpt.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping("")
    public ResponseEntity<Object> create(@Valid @RequestBody PaymentDTO paymentDTO) {
        try {
            Payment payment = new Payment();
            
            BeanUtils.copyProperties(paymentDTO, payment);
            Optional<Booking> bookingOpt = bookingRepository.findById(paymentDTO.getReservaId());
            if (bookingOpt.isPresent()) {
                Booking booking = bookingOpt.get();
                payment.setReserva(booking); // Associe o pagamento ao Booking
                booking.setPagamento(payment); // Associe o Booking ao pagamento (se necessário)
                paymentRepository.save(payment);
                return ResponseEntity.status(HttpStatus.CREATED).body(payment);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Booking não encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha ao criar pagamento");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @Valid @RequestBody PaymentDTO paymentDTO) {
        Optional<Payment> paymentOpt = paymentRepository.findById(id);

        if (paymentOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Payment paymentToUpdate = paymentOpt.get();
        BeanUtils.copyProperties(paymentDTO, paymentToUpdate);

        try {
            paymentToUpdate.setId(id);
            paymentRepository.save(paymentToUpdate);
            return ResponseEntity.ok(paymentToUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha ao atualizar pagamento");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        Optional<Payment> paymentOpt = paymentRepository.findById(id);

        if (paymentOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            paymentRepository.delete(paymentOpt.get());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
