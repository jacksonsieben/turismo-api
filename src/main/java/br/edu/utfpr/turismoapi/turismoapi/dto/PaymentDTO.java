package br.edu.utfpr.turismoapi.turismoapi.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
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
    @NotBlank(message = "Reserva é obrigatório!")
    private UUID reservaId; 

    @NotBlank(message = "Valor é obrigatório!")
    @Positive
    private double valor;
    
    @NotBlank(message = "Data de pagamento é obrigatória!")
    @PastOrPresent
    private LocalDateTime dataPagamento;
}
