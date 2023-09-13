package br.edu.utfpr.turismoapi.turismoapi.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private UUID reservaId; // Adicione o campo UUID para o ID da reserva (Booking)
    private double valor;
    private LocalDateTime dataPagamento;
}
