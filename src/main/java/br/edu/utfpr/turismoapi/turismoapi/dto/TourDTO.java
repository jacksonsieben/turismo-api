package br.edu.utfpr.turismoapi.turismoapi.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class TourDTO {
    @NotNull(message = "Preço é obrigatório!")
    @Positive
    private double preco;

    @NotBlank(message = "Itinerário é obrigatório!")
    private String itinerario;

    @NotBlank(message = "Destino é obrigatório!")
    private String destino;

    @NotBlank(message = "Agência é obrigatório!")
    private String agenciaId;

}
