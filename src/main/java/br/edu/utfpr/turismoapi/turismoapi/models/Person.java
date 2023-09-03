package br.edu.utfpr.turismoapi.turismoapi.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.edu.utfpr.turismoapi.turismoapi.utils.TipoPessoaEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_person")
public class Person extends BaseModel {
    private String nome;
    private String email;
    @Column(nullable = true)
    private LocalDateTime nascimento;
    private TipoPessoaEnum tipo;
    private String cpf;

    @OneToMany(mappedBy = "agencia")
    private List<Tour> passeios;

    @OneToMany(mappedBy = "cliente")
    private List<Booking> reservasCliente;

    @OneToMany(mappedBy = "agencia")
    private List<Booking> reservasAgencia;


    public Person(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt, String nome, String email,
            LocalDateTime nascimento, TipoPessoaEnum tipo, String cpf) {
        super(id, createdAt, updatedAt);
        this.nome = nome;
        this.email = email;
        this.nascimento = nascimento;
        this.tipo = tipo;
        this.cpf = cpf;
    }
}
