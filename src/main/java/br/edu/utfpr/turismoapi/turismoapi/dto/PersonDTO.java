package br.edu.utfpr.turismoapi.turismoapi.dto;

import java.time.LocalDateTime;

import br.edu.utfpr.turismoapi.turismoapi.utils.TipoPessoaEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
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
public class PersonDTO {
    @NotBlank(message = "Nome é obrigatório!")
    @Size(min = 2, max = 100, 
        message = "O nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotBlank(message = "E-mail é obrigatório!")
    @Email(message = "Formato de e-mail inválido")
    private String email;

    @PastOrPresent
    private LocalDateTime nascimento;
    @NotBlank(message= "CPF é obrigatório!")
    @Size(min=11, max =14, message = "O CPF/CNPJ deve ter entre 11 e 14 digitos. Digite apenas números")
    private String cpfCnpj;
    @NotNull(message = "Tipo é obrigatório")
    private TipoPessoaEnum tipo;
}
