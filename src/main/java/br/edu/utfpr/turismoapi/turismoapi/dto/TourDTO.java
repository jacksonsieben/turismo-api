package br.edu.utfpr.turismoapi.turismoapi.dto;

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
public class TourDTO {
    private double preco;
    private String itinerario;
    private String destino;
    private UUID agenciaId;

}
