package br.edu.utfpr.turismoapi.turismoapi.models;

import java.time.LocalDateTime;
import java.util.List;

import br.edu.utfpr.turismoapi.turismoapi.utils.TipoPessoaEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_person")
public class Person extends BaseModel {
    private String nome;
    private String email;
    @Column(nullable = true)
    private LocalDateTime nascimento;
    private TipoPessoaEnum tipo;
    private String cpf;

    @OneToMany(mappedBy = "agencia", fetch = FetchType.EAGER)
    private List<Tour> passeios;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    private List<Booking> reservasCliente;

    @OneToMany(mappedBy = "agencia", fetch = FetchType.EAGER)
    private List<Booking> reservasAgencia;

}
