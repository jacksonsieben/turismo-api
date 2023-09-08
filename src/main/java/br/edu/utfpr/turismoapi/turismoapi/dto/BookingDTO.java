package br.edu.utfpr.turismoapi.turismoapi.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID; // Importe a classe UUID para utilizar UUIDs

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
    private LocalDateTime dataInicial;
    private LocalDateTime dataFinal;
    private UUID clienteId; // Adicione o campo UUID para o cliente
    private UUID agenciaId; // Adicione o campo UUID para a agência
    private List<UUID> passeiosIds; // Adicione uma lista de UUIDs para os passeios
}
