package br.edu.utfpr.turismoapi.turismoapi.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID; // Importe a classe UUID para utilizar UUIDs

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
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
public class BookingDTO {
    @PastOrPresent
    @NotBlank(message = "Data inicial é obrigatória!")
    @PastOrPresent
    private LocalDateTime dataInicial;

    @NotBlank(message = "Data final é obrigatória!")
    private LocalDateTime dataFinal;

    @NotBlank(message = "Cliente é obrigatório!")
    private UUID clienteId; 

    @NotBlank(message = "Agência é obrigatória!")
    private UUID agenciaId;
    
    @NotBlank(message = "Lista de passeios é obrigatória!")
    private List<UUID> passeiosIds; 
}
