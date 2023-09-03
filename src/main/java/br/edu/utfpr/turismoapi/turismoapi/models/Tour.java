package br.edu.utfpr.turismoapi.turismoapi.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_tour")
public class Tour extends BaseModel{
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="agencia_id", nullable = false)
    private Person agencia;

    private double preco;
    private String itinerario;
    private String destino;
    @ManyToMany(mappedBy = "passeios")
    private List<Booking> reservas;

    public Tour(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt, Person agencia, double preco,
            String itinerario, String destino) {
        super(id, createdAt, updatedAt);
        this.agencia = agencia;
        this.preco = preco;
        this.itinerario = itinerario;
        this.destino = destino;
    }
    
}
