package br.edu.utfpr.turismoapi.turismoapi.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_tour")
public class Tour extends BaseModel{
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="agencia_id", nullable = false)
    private Person agencia;

    private double preco;
    private String itinerario;
    private String destino;
    private String imagem;
    @ManyToMany(mappedBy = "passeios")
    @JsonIgnore
    private List<Booking> reservas;

}
